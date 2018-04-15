package ru.back.etl;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.back.etl.Utils.DataSourceUtil;
import ru.back.etl.model.CopyDataSource;
import ru.back.etl.model.DataSource;
import ru.back.etl.service.CopyDataSourceService;
import ru.back.etl.service.DataDestinationService;
import ru.back.etl.service.DataSourceService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SpringMain {

    // field for testing
    public static final int THREAD_NUMBER = 10;
    public final static ExecutorService executor = Executors.newFixedThreadPool(SpringMain.THREAD_NUMBER);

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        final int chunk;
        int bufferchunk = 50;
        if(args.length != 0 && args[0] != null) {
            try {
                bufferchunk = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Chunk must be integer");
            }
        }
        chunk = bufferchunk;
        System.out.println("Chunk equals " + chunk);


        long begin_time = System.currentTimeMillis();
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            // Get beans of services
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            DataSourceService sourceService = appCtx.getBean(DataSourceService.class);
            CopyDataSourceService copyDataSourceService = appCtx.getBean(CopyDataSourceService.class);
            DataDestinationService dataDestinationService = appCtx.getBean(DataDestinationService.class);

            // Starting or restarting process.
            // Get list of source flight numbers
            List<String> distinctFlightNumbers = sourceService.getDistinctFlightNumber();
            // Get list of saved flight numbers
            List<String> copyBuffer = copyDataSourceService.getDistinctFlightNumber();
            // Deleting list of saved flight numbers from list of source flight numbers
            distinctFlightNumbers.removeAll(copyBuffer);

            int threadNumber = Runtime.getRuntime().availableProcessors() - 1;
            final int dfnSize = distinctFlightNumbers.size();
            final int bunchSize = dfnSize / chunk + 1;
            System.out.println("Total bunches number " + bunchSize);

            // Main parallel process
            new ForkJoinPool(threadNumber).submit(
                    () -> IntStream.range(0, bunchSize)
                            .parallel()
                            .forEach(bunch -> {
                                DataSource buffer;
                                List<CopyDataSource> cdfs = new ArrayList<>();
                                int end = (bunch + 1) * chunk < dfnSize ? (bunch + 1) * chunk : dfnSize;
                                List<String> flightNumbers = distinctFlightNumbers.subList(bunch * chunk, end);
                                Map<String, List<DataSource>> dataSources = sourceService.getByFlightNumbers(flightNumbers);
                                for (Map.Entry<String, List<DataSource>> entry : dataSources.entrySet()) {
                                    buffer = DataSourceUtil.transformToOne(entry.getValue());
                                    if (buffer.getId() != null)
                                        cdfs.add(new CopyDataSource(buffer));
                                }

                                // All records deleting of current flight number and saving one record in source
                                sourceService.deleteAllFlightNumbersAndSaveTransformed(flightNumbers, DataSourceUtil.converCopyDataSourcesToDataSources(cdfs));
                                // Save record in destination data table
                                dataDestinationService.saveAll(DataSourceUtil.convertCopyDataSourcesToDataDestinations(cdfs));
                                // Saving record in copy of source on purpose storing the processed states
                                copyDataSourceService.saveList(cdfs);

                                System.out.println("Bunch № " + bunch);
                            })).get();
        }

        System.out.println("Duration of operations is " + (System.currentTimeMillis() - begin_time));
        executor.shutdown();
    }



    /*public static void main(String[] args) throws IOException {

        long begin_time = System.currentTimeMillis();
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            DataSourceService sourceService = appCtx.getBean(DataSourceService.class);
            CopyDataSourceService copyDataSourceService = appCtx.getBean(CopyDataSourceService.class);
            DataDestinationService dataDestinationService =appCtx.getBean(DataDestinationService.class);

*//*
            List<DataSource> sources = sourceService.getAllByGroupByFlightNumber();
            *//*
     *//*List<DataSource> differentFlightNubers = sources
                    .stream()
                    .filter(x->)*//**//*


     *//*
     *//*            List<String> differentFlightNubers = new ArrayList<>();
            String flagFlightNumber = "";
            for (DataSource ds : sources) {
                if (!flagFlightNumber.equals(ds.getFlightnumber()) && !differentFlightNubers.contains(ds.getFlightnumber())) {
                    differentFlightNubers.add(ds.getFlightnumber());
                    flagFlightNumber = ds.getFlightnumber();
                }
            }

            differentFlightNubers.forEach(System.out::println);
            sourceService.getAllByGroupByFlightNumber().forEach(ds -> System.out.println(ds.getFlightnumber() + " " + ds.getArr_apt_name_es()));
            System.out.println(differentFlightNubers.size());*//**//*




            String flagFN = "";
            int chunk = 100;
            List<CopyDataSource> copyDataSources = new ArrayList<>();
            DataSource buffer;
            List<DataSource> sourceDeque = new ArrayList<>();

            for (DataSource ds : sources) {
                if (flagFN.equals(ds.getFlightnumber())) {
                    sourceDeque.add(ds);
                }
                else
                {
                    if(sourceDeque.size() != 0) {
                        buffer = DataSourceUtil.transformToOne(sourceDeque);
                        copyDataSources.add(new CopyDataSource(buffer));
                        chunk--;

                        //dataSourceService.create(new CopyDataSource(buffer));
                        if(chunk<1) {
                            copyDataSourceService.saveList(copyDataSources);
                            chunk = 100;
                            copyDataSources = new ArrayList<>();
                        }
                    }

                    flagFN = ds.getFlightnumber();
                    sourceDeque = new ArrayList<>();
                    sourceDeque.add(ds);
                }
            }
            if(copyDataSources.size() != 0)copyDataSourceService.saveList(copyDataSources);
*//*

            List<String> distinctFlightNumber = sourceService.getDistinctFlightNumber();
            List<String> copyBuffer = copyDataSourceService.getDistinctFlightNumber();
            distinctFlightNumber.removeAll(copyBuffer);


            int chunk = 100;
            List<CopyDataSource> copyDataSources = new ArrayList<>();
            DataSource buffer;

            for (int i = 0; i < distinctFlightNumber.size(); i++) {
                String flightNumber = distinctFlightNumber.get(i);
                List<DataSource> dataSources = sourceService.getByFlightNumber(flightNumber);

*//*                // delete duplicates
                dataSources = dataSources.stream()
                        .distinct()
                        .collect(Collectors.toList());*//*

                buffer = DataSourceUtil.transformToOne(dataSources);
                copyDataSources.add(new CopyDataSource(buffer));
                chunk--;

                if (chunk < 1) {
                    copyDataSourceService.saveList(copyDataSources);
                    sourceService.deleteAllFlightNumbersAndSaveTransformed(DataSourceUtil.converCopyDataSourcesToDataSources(copyDataSources));

                    // dataDestinationService.saveAll(DataSourceUtil.converCopyDataSourcesToDataDestinations(copyDataSources));

                    copyDataSources = new ArrayList<>();
                    chunk = 100;
                }
            }
            if(copyDataSources.size() != 0)copyDataSourceService.saveList(copyDataSources);

//            for (DataSource ds: sourceService.getByFlightNumber("0516"))
//            {
//                System.out.println(ds.getFlightnumber() + " " + ds.getArr_apt_name_es());
//            }
        }

        System.out.printf("I'm alive!!!");

        System.out.println("Duration of operations is " + (System.currentTimeMillis() - begin_time));

        // create short dump
//        printFile("E:\\aenaflight_2017_01_dump_20180327_1125.sql");
//        copyFile("G:\\JAVA\\aenaflight_2017_01_dump_20180327_1125.sql", "G:\\JAVA\\copy_aenaflight.sql");
    }*/

    public static void printFile(String filename) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String line = new String();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static void copyFile(String filename, String fileoutput) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileoutput)))) {
            String line = new String();
            int numberLines = 100000;
            while ((line = reader.readLine()) != null && numberLines-- > 0) {
                System.out.println(line);
                writer.newLine();
                writer.write(line);
            }
        }
    }
}
