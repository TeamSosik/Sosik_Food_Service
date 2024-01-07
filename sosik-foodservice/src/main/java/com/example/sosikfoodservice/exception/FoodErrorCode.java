package com.example.sosikfoodservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FoodErrorCode {

    INVALID_PARAMETERS(HttpStatus.BAD_REQUEST, "유효하지 않은 값입니다."),
    FOOD_NOT_FOUND(HttpStatus.NOT_FOUND, "음식데이터를 찾지 못했습니다.");

    HttpStatus httpStatus;
    String message;

    FoodErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
