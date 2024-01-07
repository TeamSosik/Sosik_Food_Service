package com.example.sosikfoodservice.repository.querycustom;

import com.example.sosikfoodservice.model.entity.FoodEntity;

import java.util.List;

public interface FoodRepositoryCustom {
    List<FoodEntity> findAllByNameContains(String name);
}
