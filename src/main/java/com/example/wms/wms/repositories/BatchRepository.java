package com.example.wms.wms.repositories;

import com.example.wms.wms.entities.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<BatchEntity, Long> {
}
