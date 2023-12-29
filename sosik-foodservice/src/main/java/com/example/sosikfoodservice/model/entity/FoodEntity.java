package com.example.sosikfoodservice.model.entity;



import com.example.sosikfoodservice.model.entity.vo.Name;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
@Table(name = "food")
public class FoodEntity  extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;
    @Embedded
    private Name name;
    @Column(precision = 10, scale = 1)
    private BigDecimal carbo;
    @Column(precision = 10, scale = 1)
    private BigDecimal protein;
    @Column(precision = 10, scale = 1)
    private BigDecimal fat;
    @Column(precision = 10, scale = 1)
    private BigDecimal kcal;
    @Column(precision = 19, scale = 10)
    private BigDecimal size;


    public FoodEntity(
            final Name name,
            final BigDecimal carbo,
            final BigDecimal protein,
            final BigDecimal fat,
            final BigDecimal kcal,
            final BigDecimal size
    ){
        this.name = name;
        this.carbo= carbo;
        this.protein= protein;
        this.fat= fat;
        this.kcal= kcal;
        this.size=size;
    }

    @Builder
    public FoodEntity(
            final String name,
    final BigDecimal carbo,
    final BigDecimal protein,
    final BigDecimal fat,
    final BigDecimal kcal,
    final BigDecimal size
    ){
        this(new Name(name), carbo, protein, fat, kcal, size);
    }

}
