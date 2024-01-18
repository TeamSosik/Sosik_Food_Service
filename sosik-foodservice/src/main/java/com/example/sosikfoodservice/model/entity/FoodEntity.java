package com.example.sosikfoodservice.model.entity;


import jakarta.persistence.*;
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
    private BigDecimal kcal;
    @Column(precision = 10, scale = 2)
    private BigDecimal size;


    @Builder
    public FoodEntity(
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy,
            Long foodId,
            String name,
            BigDecimal carbo,
            BigDecimal protein,
            BigDecimal fat,
            BigDecimal kcal,
            BigDecimal size) {
        super(createdAt, createdBy, modifiedAt, modifiedBy);
        this.foodId = foodId;
        this.name = name;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.kcal = kcal;
        this.size = size;
    }
}
