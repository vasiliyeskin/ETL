package ru.back.etl.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.back.etl.model.CopyDataSource;
import ru.back.etl.model.DataSource;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudCopyDataSourceRepository extends JpaRepository<CopyDataSource,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CopyDataSource b WHERE b.id=:id")
    int delete(@Param("id") int id);

    @Override
    CopyDataSource save(CopyDataSource dataSource);

    Optional<CopyDataSource> findById(Long id);

    @Override
    List<CopyDataSource> findAll(Sort sort);

    List<CopyDataSource> findByFlightnumber(String s);

    @Query("SELECT DISTINCT ds.flightnumber FROM CopyDataSource ds")
    List<String> findDistinctFlightNumber();
}
