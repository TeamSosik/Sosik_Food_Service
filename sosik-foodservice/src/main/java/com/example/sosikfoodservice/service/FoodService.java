package com.example.sosikfoodservice.service;

import com.example.sosikfoodservice.dto.GetFood;
import com.example.sosikfoodservice.dto.GetFoodListCondition;
import org.springframework.data.domain.Page;

public interface FoodService {
    Page<GetFood> getFoodList(GetFoodListCondition condition);
}
