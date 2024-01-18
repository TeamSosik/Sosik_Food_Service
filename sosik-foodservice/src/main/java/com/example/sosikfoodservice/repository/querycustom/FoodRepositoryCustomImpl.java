package com.example.sosikfoodservice.repository.querycustom;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.model.entity.QFoodEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FoodRepositoryCustomImpl implements FoodRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    QFoodEntity foodEntity = QFoodEntity.foodEntity;

    @Override
    public List<FoodEntity> find10FoodBySearch(String name) {
        return jpaQueryFactory.select(foodEntity)
                .from(foodEntity)
                .where(foodEntity.name.like(name+"%"))
                .orderBy(foodEntity.name.length().asc())
                .limit(10)
                .fetch();
        }

}




