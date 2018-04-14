package ru.back.etl.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.back.etl.model.DataDestination;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataDestinationRepository {
    private static final Sort SORT_ID = new Sort("id");

    @Autowired
    private CrudDataDestinationRepository repository;


    public DataDestination save(DataDestination dataDestination) {
        return repository.save(dataDestination);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public DataDestination get(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<DataDestination> getAll() {
        return repository.findAll(SORT_ID);
    }

    @Transactional
    public List<DataDestination> saveList(List<DataDestination> dataDestinations) {
        List<DataDestination> destinations = new ArrayList<>();

        for (DataDestination dd:dataDestinations) {
            destinations.add(repository.save(dd));
        }
        return destinations;
    }
}
