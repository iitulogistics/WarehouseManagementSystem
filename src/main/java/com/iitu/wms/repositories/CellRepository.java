package com.iitu.wms.repositories;

import com.iitu.wms.entities.CellEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CellRepository extends JpaRepository<CellEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update CellEntity s set s.max_count_object = ?2 where s.id = ?1")
    void updateMaxCountObject(Long id,  int count);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update CellEntity s set s.count_object = ?2 where s.id = ?1")
    void updateCountObject(Long id,  int count);

    @Query("select s from CellEntity s where not s.max_count_object = s.count_object")
    List<CellEntity> getLooseStillage();

    @Query("select s from CellEntity s where  s.stillage = ?1 and s.shelf = ?2 and s.cell = ?3")
    CellEntity getCellByIndexes(int stillage_index, int shelf_index, int cell_index);

    @Query("select s from CellEntity s where  s.bar_code = ?1")
    CellEntity getCellByBarCode(String code);
}
