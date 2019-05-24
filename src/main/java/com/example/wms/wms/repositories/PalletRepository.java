package com.example.wms.wms.repositories;

import com.example.wms.wms.entities.PalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PalletRepository extends JpaRepository<PalletEntity, Long> {

    @Query("select p from PalletEntity p where p.stillageId = ?1")
    List<PalletEntity> getPalletsByStillageId(Long id);

    @Query("select p from PalletEntity p where p.product_id = ?1")
    List<PalletEntity> getPalletsByProductId(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update PalletEntity p set p.batch_id = ?2 where p.id = ?1")
    void updateById(Long id,  long batch_id);
}
