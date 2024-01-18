package com.example.sosikfoodservice.service;

import com.example.sosikfoodservice.dto.request.GetFoodPageCondition;
import com.example.sosikfoodservice.dto.response.GetFood;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FoodService {
    Page<GetFood> getFoodPage(GetFoodPageCondition condition);

    GetFood getFood(Long id);

    List<GetFood> getFoodName(String inputValue);
}
