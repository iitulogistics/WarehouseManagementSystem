package com.iitu.wms.repositories;

import com.iitu.wms.entities.TaskEntity;
import com.iitu.wms.entities.User;
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

    @Query("select task from TaskEntity task where task.done is not null and task.user = ?1")
    List<TaskEntity> getDoneTasksByUser(User user);

    @Query("select task from TaskEntity task where task.done is null and task.user = ?1")
    List<TaskEntity> getCurrentTasksByUser(User user);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update TaskEntity task set task.user = ?2 where task.id = ?1")
    void setTaskUserByTaskId(Long id, User user);
}
