package com.example.wms.wms.repositories;

import com.example.wms.wms.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select b from OrderEntity b where b.order_number = ?1")
    List<OrderEntity> getBatchByOrderNumber(Long order_number);
}
