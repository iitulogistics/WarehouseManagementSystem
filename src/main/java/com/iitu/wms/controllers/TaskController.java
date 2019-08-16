package com.iitu.wms.controllers;

import com.iitu.wms.entities.TaskEntity;
import com.iitu.wms.entities.User;
import com.iitu.wms.repositories.TaskRepository;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Api(tags = {"Задачи"}, description = "API контроля задач")
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("")
    public ModelAndView main(@AuthenticationPrincipal User user) {
        Map<String, Object> root = new TreeMap<>();
        root.put("myDoneTask", taskRepository.getDoneTasksByUser(user));
        root.put("currentTask", taskRepository.getCurrentTasks());
        root.put("myCurrentTask", taskRepository.getCurrentTasksByUser(user));
        root.put("user", user);
        return new ModelAndView("tasks", root);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping("/allCurrentTasks")
    public List<TaskEntity> getAllCurrentTasks() {
        return taskRepository.getCurrentTasks();
    }

    @GetMapping("/allDoneTasks")
    public List<TaskEntity> getAllDoneTasks() {
        return taskRepository.getDoneTasks();
    }

    @PostMapping("/done")
    public ResponseEntity<?> taskDone(@RequestParam Long id) {
        taskRepository.taskDone(id, new Date());
        return ResponseEntity.ok("Задача с id = " + id + " выполнена");
    }

    @PostMapping("/takeTask")
    public ResponseEntity<?> takeTask(@AuthenticationPrincipal User user,
                                      @RequestParam Long task_id) {
        taskRepository.setTaskUserByTaskId(task_id, user);
        return ResponseEntity.ok("Задание присвоено работнику с именем " + user.getUsername());
    }
}