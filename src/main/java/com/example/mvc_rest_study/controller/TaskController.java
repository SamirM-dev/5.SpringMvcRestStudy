package com.example.mvc_rest_study.controller;

import com.example.mvc_rest_study.entity.Task;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final Map<Long,Task> storage = new HashMap<>();
    private Long currentId=1L;

    @GetMapping
    public ResponseEntity<List<Task>> getAll(@RequestParam(required = false) String status){
        List<Task> allTasks = new ArrayList<>(storage.values());
        if(status!=null){
            allTasks=allTasks.stream().filter(task -> task.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
        }
        return ResponseEntity.ok(allTasks);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id){
        if (id<=0) {
            throw new IllegalArgumentException();
        }
        if (!storage.containsKey(id)){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(storage.get(id));
    }
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task){
        Task created = new Task();
        created.setId(currentId);
        created.setTitle(task.getTitle());
        created.setDescription(task.getDescription());
        created.setStatus("new");
        currentId++;
        storage.put(created.getId(), created);
        return ResponseEntity.created(URI.create("/api/tasks/"+created.getId())).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id,@RequestBody Task task){
        if (id<=0) {
            throw new IllegalArgumentException();
        }
        if (!storage.containsKey(id)){
            return  ResponseEntity.notFound().build();
        }
        Task updated = storage.get(id);
        updated.setDescription(task.getDescription());
        updated.setTitle(task.getTitle());
        updated.setStatus(task.getStatus());
        storage.put(id,updated);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (id<=0) {
            throw new IllegalArgumentException();
        }
        if (!storage.containsKey(id)){
            return  ResponseEntity.notFound().build();
        }
        storage.remove(id);
        return ResponseEntity.noContent().build();
    }


}
