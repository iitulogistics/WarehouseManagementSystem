package com.example.wms.wms.controllers;

import com.example.wms.wms.entities.TaskEntity;
import com.example.wms.wms.repositories.TaskRepository;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Api(tags = {"Задачи"}, description = "API контроля задач")
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @PostMapping("/allCurrentTasks")
    public List<TaskEntity> getAllCurrentTasks() {
        return taskRepository.getCurrentTasks();
    }

    @PostMapping("/allDoneTasks")
    public List<TaskEntity> getAllDoneTasks() {
        return taskRepository.getDoneTasks();
    }

    @PostMapping("/done")
    public ResponseEntity<?> taskDone(Long id) {
        taskRepository.taskDone(id, new Date());
        return ResponseEntity.ok("Задача с id = " + id + " выполнена");
    }

}