package com.example.sosikfoodservice.repository;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.querycustom.FoodRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity, Long>, FoodRepositoryCustom {

    List<FoodEntity> findByNameContainingOrderByNameDesc(String name);

    Page<FoodEntity> findPageByNameContainingOrderByNameDesc(String name, Pageable pageable);


//    List<FoodEntity> findTop10ByNameContainsOrderByName(String name);
}
