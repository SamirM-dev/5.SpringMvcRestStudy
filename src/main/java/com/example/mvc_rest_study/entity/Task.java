package com.example.mvc_rest_study.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
    private String internalNote;
    private LocalDateTime createdAt;

    public  Task() {
    }
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = "NEW";
        createdAt = LocalDateTime.now();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInternalNote(String internalNote) {
        this.internalNote = internalNote;
    }
}
