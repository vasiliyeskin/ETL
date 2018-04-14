package ru.back.etl.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.back.etl.model.CopyDataSource;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CopyDataSourceRepository {
    private static final Sort SORT_ID = new Sort("id");
    private static final Sort SORT_FlightNumber = new Sort("flightnumber");

    @Autowired
    private CrudCopyDataSourceRepository repository;


    public CopyDataSource save(CopyDataSource copyDataSource) {
        return repository.save(copyDataSource);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public CopyDataSource get(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<CopyDataSource> getAll() {
        return repository.findAll(SORT_ID);
    }

    public List<CopyDataSource> getAllByGroupByFlightNumber()
    {
        return repository.findAll(SORT_FlightNumber);
    }

    public List<CopyDataSource> getByFlightNumber(String s) {
        return repository.findByFlightnumber(s);
    }

    @Transactional
    public List<CopyDataSource> saveList(List<CopyDataSource> copyDataSource) {
        List<CopyDataSource> copyDataSources = new ArrayList<>();

        copyDataSource.forEach(x->copyDataSources.add(repository.save(x)));
        return copyDataSources;
    }

    public List<String> getDistinctFlightNumber() {
        return repository.findDistinctFlightNumber();
    }
}
