package com.example.mvc_rest_study.controller;

import com.example.mvc_rest_study.dtotask.CreateTaskRequest;
import com.example.mvc_rest_study.dtotask.TaskResponse;
import com.example.mvc_rest_study.dtotask.UpdateTaskRequest;
import com.example.mvc_rest_study.entity.Task;
import com.example.mvc_rest_study.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        //так как пока нет глобального обработчика , часть проверок которая должна быть в сервисе пока будет в контроллере
        if(id <=0){
            return ResponseEntity.badRequest().build();
        }
        TaskResponse taskResponse=taskService.findById(id);
        if (taskResponse == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskResponse);

    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> create(@RequestBody CreateTaskRequest request){
        TaskResponse response = taskService.createTask(request);
        return ResponseEntity.created(URI.create("/api/v1/tasks/"+response.getId())).body(response);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id,@RequestBody UpdateTaskRequest request){
        if(id <=0){
            return ResponseEntity.badRequest().build();
        }
        TaskResponse response = taskService.updateTask(id, request);
        if (response == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(id <=0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }


}
