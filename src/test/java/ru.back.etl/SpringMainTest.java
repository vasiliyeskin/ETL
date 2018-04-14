package ru.back.etl;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.back.etl.Utils.DataSourceUtil;
import ru.back.etl.model.CopyDataSource;
import ru.back.etl.model.DataSource;
import ru.back.etl.service.CopyDataSourceService;
import ru.back.etl.service.DataDestinationService;
import ru.back.etl.service.DataSourceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class SpringMainTest {
    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private DataSourceService sourceService;
    @Autowired
    private CopyDataSourceService copyDataSourceService;
    @Autowired
    private DataDestinationService dataDestinationService;

    @Test
    public void testParallel() throws InterruptedException, ExecutionException {

        List<String> distinctFlightNumbers = sourceService.getDistinctFlightNumber();
        List<String> copyBuffer = copyDataSourceService.getDistinctFlightNumber();
        distinctFlightNumbers.removeAll(copyBuffer);

        int threadNumber = Runtime.getRuntime().availableProcessors() - 1;
        final int dfnSize = distinctFlightNumbers.size();
        final int chunk = 50;
        final int bunchSize = dfnSize / chunk + 1;
        System.out.println("Total bunches number " + bunchSize);

        new ForkJoinPool(threadNumber).submit(
                () -> IntStream.range(0, bunchSize)
                        .parallel()
                        .forEach(bunch -> {
                            DataSource buffer;
                            List<CopyDataSource> cdfs = new ArrayList<>();
                            int end = (bunch + 1) * chunk < dfnSize ? (bunch + 1) * chunk : dfnSize;
                            List<String> flightNumbers = distinctFlightNumbers.subList(bunch * chunk,end);
                            Map<String, List<DataSource>> dataSources = sourceService.getByFlightNumbers(flightNumbers);
                            for (Map.Entry<String,List<DataSource>> entry: dataSources.entrySet()) {
                                buffer = DataSourceUtil.transformToOne(entry.getValue());
                                if(buffer.getId() != null)
                                    cdfs.add(new CopyDataSource(buffer));
                            }
                            copyDataSourceService.saveList(cdfs);
                            sourceService.deleteAllFlightNumbersAndSaveTransformed(flightNumbers, DataSourceUtil.converCopyDataSourcesToDataSources(cdfs));
                            dataDestinationService.saveAll(DataSourceUtil.converCopyDataSourcesToDataDestinations(cdfs));

                            System.out.println("Bunch № " + bunch);
                        })).get();
    }

    @Test
    public void testMultiThreads() throws IOException, InterruptedException, ExecutionException {

        List<String> distinctFlightNumbers = sourceService.getDistinctFlightNumber();
        List<String> copyBuffer = copyDataSourceService.getDistinctFlightNumber();
        distinctFlightNumbers.removeAll(copyBuffer);


        List<CopyDataSource> copyDataSources = new ArrayList<>();
        List<Callable<List<CopyDataSource>>> callables = new ArrayList<>();
        final int dfnSize = distinctFlightNumbers.size();
        final int chunk = 50;
        final int bunchSize = dfnSize / chunk + 1;
        System.out.println("Total bunches number " + bunchSize);
        int j = 0;

        while (j < bunchSize) {
            for (int k = 0; k < SpringMain.THREAD_NUMBER; k++) {
                final int bunch = j;
                callables.add(() ->
                {
                    List<CopyDataSource> cdfs = new ArrayList<>();
                    DataSource buffer;
                    int end = (bunch + 1) * chunk < dfnSize ? (bunch + 1) * chunk : dfnSize;
                    List<String> flightNumbers = distinctFlightNumbers.subList(bunch * chunk,end);
                    Map<String, List<DataSource>> dataSources = sourceService.getByFlightNumbers(flightNumbers);
                    for (Map.Entry<String,List<DataSource>> entry: dataSources.entrySet()) {
                        buffer = DataSourceUtil.transformToOne(entry.getValue());
                        if(buffer.getId() != null)
                            cdfs.add(new CopyDataSource(buffer));
                    }

                    return cdfs;
                });
                j++;
                if(j > dfnSize / chunk)break;
            }
            System.out.println("Bunch № " + j);
            List<Future<List<CopyDataSource>>> futures = SpringMain.executor.invokeAll(callables);
            for (Future<List<CopyDataSource>> f: futures) {
                copyDataSources.addAll(f.get());
            }

            copyDataSourceService.saveList(copyDataSources);
            sourceService.deleteAllFlightNumbersAndSaveTransformed(DataSourceUtil.converCopyDataSourcesToDataSources(copyDataSources));
            dataDestinationService.saveAll(DataSourceUtil.converCopyDataSourcesToDataDestinations(copyDataSources));
            callables = new ArrayList<>();
            copyDataSources = new ArrayList<>();
        }

        SpringMain.executor.shutdown();

        List<String> flightNumbers = sourceService.getDistinctFlightNumber();
        copyBuffer = copyDataSourceService.getDistinctFlightNumber();
        flightNumbers.removeAll(copyBuffer);
        sourceService.deleteAllByFlightNumbers(flightNumbers);
    }

}