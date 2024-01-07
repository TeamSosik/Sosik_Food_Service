package com.example.sosikfoodservice.exception;

import lombok.Getter;

@Getter
public class FoodException extends RuntimeException {

    private final FoodErrorCode errorCode;

    public FoodException(FoodErrorCode foodErrorCode) {
        this.errorCode = foodErrorCode;
    }
}
