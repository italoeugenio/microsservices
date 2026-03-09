package com.ms.stock_control_api.repository;

import com.ms.stock_control_api.entity.entities.WatchModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WatchRepository extends JpaRepository<WatchModel, UUID>, JpaSpecificationExecutor<WatchModel> {
    Page<WatchModel> findAll(Pageable pageable);

    @Query("""
    SELECT w FROM watch w INNER JOIN w.brand b WHERE LOWER(b.name) LIKE(:brandName)
    """)
    Page<WatchModel> findAllByBrandName(@Param("brandName") String brandName, Pageable pageable);

}
