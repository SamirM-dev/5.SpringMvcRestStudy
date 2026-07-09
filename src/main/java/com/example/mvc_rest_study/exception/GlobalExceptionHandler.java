package com.example.mvc_rest_study.exception;


import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request){
        ErrorResponse response =new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "BAD REQUEST",e.getMessage(),request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e, HttpServletRequest request){
        ErrorResponse response =new ErrorResponse(HttpStatus.NOT_FOUND.value(), "NOT FOUND",e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
     public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(ResourceAlreadyExistsException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), "CONFLICT", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        List<ErrorResponse.FieldError> fieldErrors = e.getBindingResult().getFieldErrors().stream().map(fieldError -> new ErrorResponse.FieldError(fieldError.getField(),fieldError.getDefaultMessage())).toList();
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "BAD REQUEST", "Ошибка валидации входных данных", request.getRequestURI());
        response.setFieldErrors(fieldErrors);
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), "CONFLICT", "Ресурс уже существует", request.getRequestURI());
        return ResponseEntity.status(409).body(response);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), "METHOD NOT ALLOWED", "HTTP-метод " + e.getMethod() + " не поддерживается для этого пути", request.getRequestURI());
        return ResponseEntity.status(405).body(response);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException e, HttpServletRequest request){ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "NOT FOUND", "Запрошенный путь не существует", request.getRequestURI());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR", e.getMessage(), request.getRequestURI());
        return ResponseEntity.internalServerError().body(response);
    }

}
