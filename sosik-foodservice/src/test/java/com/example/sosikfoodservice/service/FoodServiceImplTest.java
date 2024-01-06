package com.example.sosikfoodservice.service;

import com.example.sosikfoodservice.dto.GetFood;
import com.example.sosikfoodservice.dto.GetFoodListCondition;
import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.FoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FoodServiceImplTest {

    @InjectMocks
    FoodServiceImpl foodServiceImpl;
    @Mock
    FoodRepository foodRepository;

    @Test
    void FoodRepository가_null이아님() {

        assertThat(foodRepository).isNotNull();
        assertThat(foodServiceImpl).isNotNull();
    }

    @Test
    void 멤버십목록조회() {

        // given
        int total = 121;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        List<FoodEntity> foodList = new ArrayList<>();

        for(long i = 0; i < total; i++) {

            FoodEntity food = null;

            if(i % 3 == 0) {
                food = createFood(i, "sdf사과asdf", 10.1, 20, 30, 50.2, 20, "A과수농장", "A과수농장", LocalDateTime.now(), LocalDateTime.now());
                count1 += 1;
            } else if(i % 3 == 1)  {
                food = createFood(i,"ok과", 10.1, 20, 30, 50.2, 20, "B과수농장", "B과수농장", LocalDateTime.now(), LocalDateTime.now());
                count2 += 1;
            } else {
                food = createFood(i,"과asdf", 10.1, 20, 30, 50.2, 20, "C과수농장", "C과수농장", LocalDateTime.now(), LocalDateTime.now());
                count3 += 1;
            }
            foodList.add(food);
        }

        String searchName = "sdf사과asdf";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);


        List<FoodEntity> searchFoodList = foodList.stream()
                .filter((food) -> {
                    return food.getName().equals(searchName);
                }).collect(toList());
        Long count = searchFoodList.stream().count();

        Page<FoodEntity> pageFoodEntity = new PageImpl<>(searchFoodList);

        Mockito.doReturn(
                        pageFoodEntity
                )
                .when(foodRepository)
                .findPageByNameContainingOrderByNameDesc(searchName, pageable);

        // when
        GetFoodListCondition condition = GetFoodListCondition.builder()
                .name(searchName)
                .page(page)
                .size(size)
                .build();

        Page<GetFood> result = foodServiceImpl.getFoodList(condition);

        // then
        assertThat(result.getTotalElements()).isEqualTo(searchFoodList.size());
        assertThat(result.getTotalPages()).isEqualTo(pageFoodEntity.getTotalPages());
    }

    private FoodEntity createFood(
            Long foodId,
            String name,
            double carbo,
            double protein,
            double fat,
            double kcal,
            double size,
            String createdBy,
            String modifiedBy,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return FoodEntity.builder()
                .foodId(foodId)
                .name(name)
                .carbo(createBigDecimal(carbo))
                .protein(createBigDecimal(protein))
                .fat(createBigDecimal(fat))
                .kcal(createBigDecimal(kcal))
                .size(createBigDecimal(size))
                .createdBy(createdBy)
                .modifiedBy(modifiedBy)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    private BigDecimal createBigDecimal(double num) {
        return new BigDecimal(num);
    }












}
