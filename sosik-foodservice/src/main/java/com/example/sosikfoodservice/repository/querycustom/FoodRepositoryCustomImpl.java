package com.example.sosikfoodservice.repository.querycustom;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.model.entity.QFoodEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class FoodRepositoryCustomImpl implements FoodRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    QFoodEntity foodEntity = QFoodEntity.foodEntity;

    @Override
    public Page<FoodEntity> findPageByNameContainingOrderByNameDesc(String name, Pageable pageable) {

        List<FoodEntity> entity = jpaQueryFactory
                .selectFrom(foodEntity)
                .where(foodEntity.name.like("%" + name + "%"))
                .orderBy(foodEntity.name.length().asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = jpaQueryFactory
                .select(foodEntity.count())
                .from(foodEntity)
                .where(foodEntity.name.like(name))
                .where(foodEntity.name.like("%" + name + "%"))
                .orderBy(foodEntity.name.length().asc())
                .fetchFirst();

        return new PageImpl<>(entity, pageable, total);
    }

    @Override
    public List<FoodEntity> find10FoodBySearch(String name) {
        return jpaQueryFactory.select(foodEntity)
                .from(foodEntity)
                .where(foodEntity.name.like(name + "%"))
                .orderBy(foodEntity.name.length().asc())
                .limit(10)
                .fetch();
    }

}




