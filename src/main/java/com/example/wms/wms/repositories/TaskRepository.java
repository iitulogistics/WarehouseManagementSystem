package com.example.wms.wms.repositories;

import com.example.wms.wms.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update TaskEntity task set task.done = ?2 where task.id = ?1")
    void taskDone(Long id, Date date);

    @Query("select task from TaskEntity task where task.done is null")
    List<TaskEntity> getCurrentTasks();

    @Query("select task from TaskEntity task where task.done is not null")
    List<TaskEntity> getDoneTasks();
}
