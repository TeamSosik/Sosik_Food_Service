package com.example.sosikfoodservice.repository;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.querycustom.FoodRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodEntity, Long>, FoodRepositoryCustom {

}
