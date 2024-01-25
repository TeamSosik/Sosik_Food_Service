package com.example.sosikfoodservice.dto.response;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.redis.CacheFood;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ResponseGetFood {

    private Long foodId;
    private String name;
    private BigDecimal carbo;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal sugars;
    private BigDecimal kcal;
    private String manufacturer;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public ResponseGetFood(Long foodId,
                           String name,
                           BigDecimal carbo,
                           BigDecimal protein,
                           BigDecimal fat,
                           BigDecimal sugars,
                           BigDecimal kcal,
                           String manufacturer,
                           String image,
                           LocalDateTime createdAt,
                           LocalDateTime modifiedAt) {
        this.foodId = foodId;
        this.name = name;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.sugars = sugars;
        this.kcal = kcal;
        this.manufacturer = manufacturer;
        this.image = image;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ResponseGetFood create(FoodEntity foodEntity) {

        return ResponseGetFood.builder()
                .foodId(foodEntity.getFoodId())
                .name(foodEntity.getName())
                .carbo(foodEntity.getCarbo())
                .protein(foodEntity.getProtein())
                .fat(foodEntity.getFat())
                .sugars(foodEntity.getSugars())
                .kcal(foodEntity.getKcal())
                .image(foodEntity.getImage())
                .manufacturer(foodEntity.getManufacturer())
                .createdAt(foodEntity.getCreatedAt())
                .modifiedAt(foodEntity.getModifiedAt())
                .build();
    }

    public static ResponseGetFood create(CacheFood redisFood) {

        return ResponseGetFood.builder()
                .foodId(redisFood.getFoodId())
                .name(redisFood.getName())
                .carbo(redisFood.getCarbo())
                .protein(redisFood.getProtein())
                .fat(redisFood.getFat())
                .sugars(redisFood.getSugars())
                .kcal(redisFood.getKcal())
                .image(redisFood.getImage())
                .manufacturer(redisFood.getManufacturer())
                .createdAt(redisFood.getCreatedAt())
                .modifiedAt(redisFood.getModifiedAt())
                .build();
    }
}
