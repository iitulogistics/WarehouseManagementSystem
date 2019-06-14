package com.example.wms.wms.repositories;

import com.example.wms.wms.base.BaseType;
import com.example.wms.wms.entities.ContainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContainerRepository extends JpaRepository<ContainerEntity, Long> {

    @Query("select p from ContainerEntity p where p.stillageId = ?1")
    List<ContainerEntity> getPalletsByStillageId(Long id);

    @Query("select p from ContainerEntity p where p.product_id = ?1")
    List<ContainerEntity> getPalletsByProductId(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update ContainerEntity p set p.batch_id = ?2 where p.id = ?1")
    void updateBatchById(Long id,  long batch_id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update ContainerEntity p set p.lifeCycle = ?2 where p.id = ?1")
    void updateLifeCyrcleById(Long id, BaseType.LifeCycle lifeCycle);
}
