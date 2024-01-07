package com.example.sosikfoodservice.repository.querycustom;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.model.entity.QFoodEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class FoodRepositoryCustomImpl implements FoodRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FoodEntity> findAllByNameContains(String name) {

        return jpaQueryFactory.select(QFoodEntity.foodEntity)
                .from(QFoodEntity.foodEntity)
                .where(nameContains(name))
                .fetch();


    }

    // contains
    private BooleanExpression nameContains(String name) {

        return StringUtils.hasText(name) ? QFoodEntity.foodEntity.name.contains(name) : null;
    }




}
