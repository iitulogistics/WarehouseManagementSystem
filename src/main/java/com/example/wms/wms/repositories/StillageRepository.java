package com.example.wms.wms.repositories;

import com.example.wms.wms.entities.StillageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StillageRepository extends JpaRepository<StillageEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update StillageEntity s set s.max_count_object = ?2 where s.id = ?1")
    void updateMaxCountObject(Long id,  int count);
}
