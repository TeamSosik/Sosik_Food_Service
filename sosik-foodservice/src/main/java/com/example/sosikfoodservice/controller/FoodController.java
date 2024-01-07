package com.example.sosikfoodservice.controller;

import com.example.sosikfoodservice.dto.response.GetFood;
import com.example.sosikfoodservice.dto.request.GetFoodPageCondition;
import com.example.sosikfoodservice.dto.response.Result;
import com.example.sosikfoodservice.exception.FoodErrorCode;
import com.example.sosikfoodservice.exception.FoodException;
import com.example.sosikfoodservice.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping("/food")
@RequiredArgsConstructor
@RestController
public class FoodController {

    private final FoodService foodService;

    /**
     * TODO : 필요한 데이터만 있는 PageDTO 만들어 보기
     */
    @GetMapping("/v1")
    public Result<Page<GetFood>> getFoodPage(@Valid GetFoodPageCondition condition) {

        System.out.println(condition.getPage());
        System.out.println(condition.getName());
        System.out.println(condition.getSize());

        Page<GetFood> result = foodService.getFoodPage(condition);

        return Result.success(result);
    }

    @GetMapping("/v1/{foodId}")
    public ResponseEntity<Result<GetFood>> getFood(@PathVariable Long foodId) {

        if(foodId < 0) {
            throw new FoodException(FoodErrorCode.INVALID_PARAMETERS);
        }

        GetFood result = foodService.getFood(foodId);

        Result<GetFood> body = Result.success(result);

        return ResponseEntity.status(HttpStatus.OK)
                .body(body);

    }



}
