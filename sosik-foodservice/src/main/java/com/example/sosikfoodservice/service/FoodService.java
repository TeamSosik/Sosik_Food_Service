package com.example.sosikfoodservice.service;

import com.example.sosikfoodservice.dto.request.RequestGetFoodPage;
import com.example.sosikfoodservice.dto.response.ResponseGetFood;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FoodService {
    Page<ResponseGetFood> getFoodPage(RequestGetFoodPage condition);

    ResponseGetFood getFood(Long id);

    List<ResponseGetFood> getFoodName(String inputValue);
}
