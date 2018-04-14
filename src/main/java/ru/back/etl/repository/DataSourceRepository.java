package ru.back.etl.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.back.etl.model.DataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DataSourceRepository {
    private static final Sort SORT_ID = new Sort("id");
    private static final Sort SORT_FlightNumber = new Sort("flightnumber");

    @Autowired
    private CrudDataSourceRepository repository;

    public DataSource save(DataSource dataSource) {
        return repository.save(dataSource);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public DataSource get(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<DataSource> getAll() {
        return repository.findAll(SORT_ID);
//        return repository.findAllByOrderByFlightnumberAscEst_arr_date_time_ltAscAct_arr_date_time_ltAsc();
    }

    public List<String> getDistinctFlightNumber() {
        return repository.findDistinctFlightNumber();
    }

    public List<DataSource> getAllByGroupByFlightNumber()
    {
        return repository.findAll(SORT_FlightNumber);
    }

    public List<DataSource> getByFlightNumber(String s) {
        return repository.findByFlightnumberOrderByIdDesc(s);
    }

    @Transactional
    public void deleteAllFlightNumbersAndSaveTransformed(List<String> flightNumbers, List<DataSource> dataSources) {
        for (String fn: flightNumbers) {
            repository.deleteFlight(fn);
        }

        for (DataSource ds:dataSources) {
            repository.save(ds);
        }
    }

    @Transactional
    public Map<String,List<DataSource>> getByFlightNumbers(List<String> flightNumbers) {
        Map<String,List<DataSource>> listMap = new HashMap<>();
        for (String flightNumber: flightNumbers) {
            listMap.put(flightNumber, repository.findByFlightnumber(flightNumber));
        }
        return listMap;
    }

    @Transactional
    public void deleteAllFlightNumbersAndSaveTransformed(List<DataSource> dataSources) {
        for (DataSource ds:dataSources) {
            repository.deleteFlight(ds.getFlightnumber());
            repository.save(ds);
        }
    }

    @Transactional
    public void deleteAllByFlightNumbers(List<String> distinctFlightNumbers) {
        for (String s:distinctFlightNumbers) {
            repository.deleteFlight(s);
        }
    }
}
