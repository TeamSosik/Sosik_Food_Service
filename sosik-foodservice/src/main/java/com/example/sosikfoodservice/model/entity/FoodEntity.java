package com.example.sosikfoodservice.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
@Table(
        name = "food",
        indexes = {
            @Index(
                    name = "idx_food_name", columnList = "name", unique = false
            )
        }
)
public class FoodEntity extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long foodId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(precision = 10, scale = 2)
    private BigDecimal carbo;
    @Column(precision = 10, scale = 2)
    private BigDecimal protein;
    @Column(precision = 10, scale = 2)
    private BigDecimal fat;
    @Column(precision = 10, scale = 2)
    private BigDecimal sugars;
    @Column(precision = 10, scale = 2)
    private BigDecimal kcal;
    private String manufacturer;
    @Column(length = 4000)
    private String image;



    @Builder
    public FoodEntity(
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            Long foodId,
            String name,
            BigDecimal carbo,
            BigDecimal protein,
            BigDecimal fat,
            BigDecimal sugars,
            BigDecimal kcal,
            String manufacturer,
            String image) {
        super(createdAt, modifiedAt);
        this.foodId = foodId;
        this.name = name;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.sugars = sugars;
        this.kcal = kcal;
        this.manufacturer = manufacturer;
        this.image = image;
    }
}
