package com.example.sosikfoodservice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetFoodListCondition {

    private String name;// 찾는 음식명
    private int page;
    private int size;

    @Builder
    public GetFoodListCondition(String name, int page, int size) {
        this.name = name;
        this.page = page;
        this.size = size;
    }
}
