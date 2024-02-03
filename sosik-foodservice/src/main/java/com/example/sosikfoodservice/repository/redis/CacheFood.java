package com.example.sosikfoodservice.repository.redis;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@RedisHash(
        value = "cacheFood",
        timeToLive = 60 * 60 * 24 // 하루
)
public class CacheFood {

    @Id
    private Long foodId;
    @Indexed
    private String name;
    private BigDecimal carbo;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal kcal;
    private BigDecimal sugars;
    private String manufacturer;
    private String image;
    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime modifiedAt; //수정일시

    @Builder
    public CacheFood(
            Long foodId,
            String name,
            BigDecimal carbo,
            BigDecimal protein,
            BigDecimal fat,
            BigDecimal kcal,
            BigDecimal sugars,
            String manufacturer,
            String image,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
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

    public static CacheFood create(FoodEntity foodEntity) {

        return CacheFood.builder()
                .foodId(foodEntity.getFoodId())
                .name(foodEntity.getName())
                .carbo(foodEntity.getCarbo())
                .protein(foodEntity.getProtein())
                .fat(foodEntity.getFat())
                .sugars(foodEntity.getSugars())
                .kcal(foodEntity.getKcal())
                .manufacturer(foodEntity.getManufacturer())
                .image(foodEntity.getImage())
                .createdAt(foodEntity.getCreatedAt())
                .modifiedAt(foodEntity.getModifiedAt())
                .build();
    }
}
