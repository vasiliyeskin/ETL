package ru.back.etl.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.back.etl.model.DataDestination;
import ru.back.etl.repository.DataDestinationRepository;

import java.util.List;

@Service
public class DataDestinationService {

    @Autowired
    private DataDestinationRepository repository;

    public DataDestination create(DataDestination dataDestination) {
        return repository.save(dataDestination);
    }

    public void delete(int id) throws NotFoundException {
        repository.delete(id);
    }

    public DataDestination get(int id) throws NotFoundException {
        return repository.get(id);
    }

    public List<DataDestination> getAll() {
        return repository.getAll();
    }

    public boolean saveAll(List<DataDestination> dataDestinations) {
        return repository.saveList(dataDestinations) != null;
    }
}
