package com.example.wms.wms.repositories;

import com.example.wms.wms.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update ProductEntity p set p.count_on_shipping = ?2 , " +
            "p.count_on_warehouse = ?3 where p.id = ?1")
    void updateById(Long id,  int count_on_shipping, int count_on_warehouse);
}
