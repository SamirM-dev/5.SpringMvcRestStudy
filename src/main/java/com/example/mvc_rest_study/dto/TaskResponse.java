package com.example.mvc_rest_study.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String status;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
