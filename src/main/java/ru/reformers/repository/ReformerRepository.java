package ru.reformers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.reformers.model.Reformer;

public interface ReformerRepository extends JpaRepository<Reformer, Long> {

    List<Reformer> findAllByOrderByIdAsc();

    List<Reformer> findByFullNameContainingIgnoreCaseOrderByIdAsc(String query);

    List<Reformer> findByEraOrderByIdAsc(String era);

    List<Reformer> findByFullNameContainingIgnoreCaseAndEraOrderByIdAsc(String query, String era);

    @Query("""
            SELECT DISTINCT r.era FROM Reformer r
            WHERE r.era IS NOT NULL AND TRIM(r.era) <> ''
            ORDER BY r.era
            """)
    List<String> findDistinctEras();
}

