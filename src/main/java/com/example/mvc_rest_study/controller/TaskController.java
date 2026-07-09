package com.example.mvc_rest_study.controller;

import com.example.mvc_rest_study.dto.CreateTaskRequest;
import com.example.mvc_rest_study.dto.TaskResponse;
import com.example.mvc_rest_study.dto.UpdateTaskRequest;
import com.example.mvc_rest_study.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public List<TaskResponse> getAll(@RequestParam(required = false) String status){
      return taskService.getAll(status);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id){
        TaskResponse taskResponse=taskService.findById(id);
        return ResponseEntity.ok(taskResponse);

    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request){
        TaskResponse response = taskService.createTask(request);
        return ResponseEntity.created(URI.create("/api/v1/tasks/"+response.getId())).body(response);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id,@Valid @RequestBody UpdateTaskRequest request){
        TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


}
