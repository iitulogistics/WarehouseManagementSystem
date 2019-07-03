package com.example.wms.wms.repositories;

import com.example.wms.wms.entities.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatchRepository extends JpaRepository<BatchEntity, Long> {
    @Query("select b from BatchEntity b where b.company_name = ?1")
    List<BatchEntity> getBatchByCompanyName(String name);
}
