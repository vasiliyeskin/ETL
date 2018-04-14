package ru.back.etl.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.back.etl.repository.DataSourceRepository;
import ru.back.etl.model.DataSource;

import java.util.List;
import java.util.Map;

@Service
public class DataSourceService {
    @Autowired
    private DataSourceRepository repository;

    public DataSource create(DataSource dataSource) {
        return repository.save(dataSource);
    }

    public void delete(int id) throws NotFoundException {
        repository.delete(id);
    }

    public DataSource get(int id) throws NotFoundException {
        return repository.get(id);
    }

    @Cacheable("datasource")
    public List<DataSource> getAll() {
        return repository.getAll();
    }

    public List<String> getDistinctFlightNumber() {
        return repository.getDistinctFlightNumber();
    }

    @Cacheable("datasource")
    public List<DataSource> getAllByGroupByFlightNumber(){return repository.getAllByGroupByFlightNumber();}

    @Cacheable("datasource")
    public List<DataSource> getByFlightNumber(String s) {
        return repository.getByFlightNumber(s);
    }

    public void deleteAllFlightNumbersAndSaveTransformed(List<String> flightNumbers, List<DataSource> dataSources) {
        repository.deleteAllFlightNumbersAndSaveTransformed(flightNumbers, dataSources);
    }

    public Map<String,List<DataSource>> getByFlightNumbers(List<String> flightNumbers) {
        return repository.getByFlightNumbers(flightNumbers);
    }

    public void deleteAllFlightNumbersAndSaveTransformed(List<DataSource> dataSources) {
        repository.deleteAllFlightNumbersAndSaveTransformed(dataSources);
    }

    public void deleteAllByFlightNumbers(List<String> distinctFlightNumbers) {
        repository.deleteAllByFlightNumbers(distinctFlightNumbers);
    }
}
