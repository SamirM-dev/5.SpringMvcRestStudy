package com.example.mvc_rest_study.dto;

import com.example.mvc_rest_study.validation.ValidTaskStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    @NotBlank(message = "Название должно быть заполнено!")
    private String title;
    @NotBlank(message = "Описание должно быть заполнено!")
    @Size(max = 500,message = "Описание должно быть короче 500 символов")
    private String description;
    @NotBlank(message = "Статус не должен быть пустым")
    @ValidTaskStatus(message = "Не корректный статус задачи")
    private String status;
}
