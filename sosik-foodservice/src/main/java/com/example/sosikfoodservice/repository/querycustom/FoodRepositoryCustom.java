package com.example.sosikfoodservice.repository.querycustom;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodRepositoryCustom {

    Page<FoodEntity> findPageByNameContainingOrderByNameDesc(String name, Pageable pageable);
    List<FoodEntity> find10FoodBySearch(String name);
}
