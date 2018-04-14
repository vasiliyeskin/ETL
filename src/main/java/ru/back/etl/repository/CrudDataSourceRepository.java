package ru.back.etl.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.back.etl.model.DataSource;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDataSourceRepository extends JpaRepository<DataSource, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM DataSource b WHERE b.id=:id")
    int delete(@Param("id") int id);

    @Override
    DataSource save(DataSource dataSource);

    Optional<DataSource> findById(Long id);

    @Override
    List<DataSource> findAll(Sort sort);

    @Query("SELECT DISTINCT ds.flightnumber FROM DataSource ds")
    List<String> findDistinctFlightNumber();

    List<DataSource> findByFlightnumberOrderByIdDesc(String s);

    @Modifying
    @Query("DELETE FROM DataSource ds WHERE ds.flightnumber=:flight")
    void deleteFlight(@Param("flight") String flight);

    @Query("SELECT ds FROM DataSource ds WHERE ds.flightnumber=:fn")
    List<DataSource> findByFlightnumber(@Param("fn") String flightNumber);
}
