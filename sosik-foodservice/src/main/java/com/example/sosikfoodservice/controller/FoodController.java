package com.example.sosikfoodservice.controller;

import com.example.sosikfoodservice.dto.request.GetFoodPageCondition;
import com.example.sosikfoodservice.dto.response.ResponseGetFood;
import com.example.sosikfoodservice.dto.response.Result;
import com.example.sosikfoodservice.exception.FoodErrorCode;
import com.example.sosikfoodservice.exception.FoodException;
import com.example.sosikfoodservice.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/food/v1")
@RequiredArgsConstructor
@RestController
public class FoodController {

    private final FoodService foodService;

    /**
     * TODO : 필요한 데이터만 있는 PageDTO 만들어 보기
     */
    @GetMapping
    public Result<Page<ResponseGetFood>> getFoodPage(@Valid GetFoodPageCondition condition) {
        Page<ResponseGetFood> result = foodService.getFoodPage(condition);
        return Result.success(result);
    }

    @GetMapping("/{foodId}")
    public Result<ResponseGetFood> getFood(@PathVariable Long foodId) {

        if (foodId < 0) {
            throw new FoodException(FoodErrorCode.INVALID_PARAMETERS);
        }

        ResponseGetFood result = foodService.getFood(foodId);

        return Result.success(result);
    }
    @GetMapping("/search")
    public Result<List<ResponseGetFood>> getFoodPage(String inputValue) {
        return Result.success(foodService.getFoodName(inputValue));
    }


}
