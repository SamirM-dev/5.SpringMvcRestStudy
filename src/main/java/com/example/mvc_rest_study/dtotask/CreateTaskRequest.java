package com.example.mvc_rest_study.dtotask;

import lombok.Data;

@Data
public class CreateTaskRequest {
    private String title;
    private String description;
}
