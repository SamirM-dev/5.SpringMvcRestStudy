package com.example.mvc_rest_study.dtotask;

import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private String status;
}
