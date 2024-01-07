package com.example.sosikfoodservice.service;

import com.example.sosikfoodservice.dto.response.GetFood;
import com.example.sosikfoodservice.dto.request.GetFoodPageCondition;
import org.springframework.data.domain.Page;

public interface FoodService {
    Page<GetFood> getFoodPage(GetFoodPageCondition condition);

    GetFood getFood(Long id);
}
