package ru.back.etl.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.back.etl.model.CopyDataSource;
import ru.back.etl.repository.CopyDataSourceRepository;

import java.util.List;

@Service
public class CopyDataSourceService {
    @Autowired
    private CopyDataSourceRepository repository;

    public CopyDataSource create(CopyDataSource copyDataSource) {
        return repository.save(copyDataSource);
    }

    public boolean saveList(List<CopyDataSource> copyDataSource) {
        return repository.saveList(copyDataSource) != null;
    }

    public void delete(int id) throws NotFoundException {
        repository.delete(id);
    }

    public CopyDataSource get(int id) throws NotFoundException {
        return repository.get(id);
    }

    public List<CopyDataSource> getAll() {
        return repository.getAll();
    }

    public List<CopyDataSource> getAllByGroupByFlightNumber(){return repository.getAllByGroupByFlightNumber();}

    public List<CopyDataSource> getByFlightNumber(String s) {
        return repository.getByFlightNumber(s);
    }

    public List<String> getDistinctFlightNumber() {
        return repository.getDistinctFlightNumber();
    }
}
