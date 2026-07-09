package com.example.mvc_rest_study.service;

import com.example.mvc_rest_study.dto.CreateTaskRequest;
import com.example.mvc_rest_study.dto.TaskResponse;
import com.example.mvc_rest_study.dto.UpdateTaskRequest;
import com.example.mvc_rest_study.entity.Task;
import com.example.mvc_rest_study.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<TaskResponse> getAll(String status){
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> taskResponses = new ArrayList<>();
        if(status != null){
            tasks=tasks.stream().filter(task -> status.equalsIgnoreCase(task.getStatus())).collect(Collectors.toList());
        }
        for (Task task : tasks) {
            taskResponses.add(toResponse(task));
        }
        return taskResponses;
    }

    public TaskResponse findById(Long id){
        if (id<=0) throw new IllegalArgumentException("Incorrect id format");
        Task task = taskRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Task with id: "+id+" does not found"));
        return toResponse(task);
    }

    public TaskResponse createTask(CreateTaskRequest request){
        Task task = new Task(request.getTitle(), request.getDescription());
        return toResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(Long id,UpdateTaskRequest request){
        if (id<=0) throw new IllegalArgumentException("Incorrect id format");
        Task task = taskRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Task with id: "+id+" does not found"));
        task.setTitle(request.getTitle());
        task.setDescription((request.getDescription()));
        task.setStatus(request.getStatus());
        return toResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id){
        if (id<=0) throw new IllegalArgumentException("Incorrect id format");
        Task task = taskRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Task with id: "+id+" does not found"));
        taskRepository.deleteById(id);
    }



    private TaskResponse toResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setStatus(task.getStatus());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setCreatedAt(task.getCreatedAt());
        return taskResponse;
    }
}

