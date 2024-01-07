package com.example.sosikfoodservice.controller;

import com.example.sosikfoodservice.exception.ErrorResult;
import com.example.sosikfoodservice.exception.FieldErrorResult;
import com.example.sosikfoodservice.exception.FoodException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FoodControllerAdvice {

    // 파라미터 검증 예외
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<FieldErrorResult> MethodArgumentNotValidEx(MethodArgumentNotValidException e) {

        FieldErrorResult body = FieldErrorResult.builder()
                .field(e.getFieldError().getField())
                .code(e.getStatusCode())
                .message(e.getFieldError().getDefaultMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(value = {FoodException.class})
    public ResponseEntity<ErrorResult> FoodEx(FoodException e) {

        ErrorResult body = ErrorResult.builder()
                .code(e.getErrorCode().getHttpStatus())
                .message(e.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(body);

    }






}
