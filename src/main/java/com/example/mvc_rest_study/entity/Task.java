package com.example.mvc_rest_study.entity;

import lombok.Data;

@Data
public class Task {
    private Long id;
    private String title;
    private String description;
    private String status;
}
