package com.example.sosikfoodservice.exception;

import lombok.Getter;

@Getter
public class FoodException extends RuntimeException {

    private final FoodErrorCode foodErrorCode;

    public FoodException(FoodErrorCode foodErrorCode) {
        this.foodErrorCode = foodErrorCode;
    }
}
