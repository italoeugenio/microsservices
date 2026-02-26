package com.ms.stock_control_api.repository;

import com.ms.stock_control_api.entity.entities.BrandModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BrandRepostiory extends JpaRepository<BrandModel, UUID> {
    Page<BrandModel> findAll (Pageable pageable);
    Page<BrandModel> findByFounded(int founded, Pageable pageable);
}
