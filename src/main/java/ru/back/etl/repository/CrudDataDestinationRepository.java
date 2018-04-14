package ru.back.etl.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.back.etl.model.DataDestination;
import ru.back.etl.model.DataSource;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDataDestinationRepository extends JpaRepository<DataDestination,Long> {


    @Transactional
    @Modifying
    @Query("DELETE FROM DataDestination b WHERE b.id=:id")
    int delete(@Param("id") int id);

    @Override
    DataDestination save(DataDestination dataSource);

    Optional<DataDestination> findById(Long id);

    @Override
    List<DataDestination> findAll(Sort sort);

}
