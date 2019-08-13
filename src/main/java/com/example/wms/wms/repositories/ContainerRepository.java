package com.example.wms.wms.repositories;

import com.example.wms.wms.base.BaseType;
import com.example.wms.wms.entities.ContainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContainerRepository extends JpaRepository<ContainerEntity, Long> {

    @Query("select p from ContainerEntity p where p.cellId.id = ?1")
    List<ContainerEntity> getContainersByCellId(Long id);

//    @Query("select p from ContainerEntity p where p.product.id = ?1")
//    List<ContainerEntity> getContainersByProductId(Long id);
//
//    @Query("select p from ContainerEntity p where p.product_id = ?1 and not p.lifeCycle = ?2")
//    List<ContainerEntity> getContainersByProductIdAndNotLifeCycle(Long id, BaseType.LifeCycle lifeCycle);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update ContainerEntity p set p.lifeCycle = ?2 where p.id = ?1")
    void updateLifeCyrcleById(Long id, BaseType.LifeCycle lifeCycle);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update ContainerEntity p set p.lifeCycle = ?2, p.cellId = ?3 where p.id = ?1")
    void updateLifeCyrcleAndStillageById(Long id, BaseType.LifeCycle lifeCycle, Long id_cell);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update ContainerEntity p set p.amount = ?2 where p.id = ?1")
    void updateCountProducts(Long id, int count);

    @Query("select c from ContainerEntity c where c.lifeCycle = ?1")
    List<ContainerEntity> getContainerByLifeCycle(BaseType.LifeCycle lifeCycle);
}
