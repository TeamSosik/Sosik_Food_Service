package com.example.sosikfoodservice.service;

import com.example.sosikfoodservice.dto.response.GetFood;
import com.example.sosikfoodservice.dto.request.GetFoodPageCondition;
import com.example.sosikfoodservice.exception.FoodErrorCode;
import com.example.sosikfoodservice.exception.FoodException;
import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.FoodRepository;
import com.example.sosikfoodservice.repository.redis.RedisFood;
import com.example.sosikfoodservice.repository.redis.RedisFoodRepository;
import org.junit.jupiter.api.Assertions;
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
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FoodServiceImplTest {

    @InjectMocks
    FoodServiceImpl foodServiceImpl;
    @Mock
    FoodRepository foodRepository;
    @Mock
    RedisFoodRepository redisFoodRepository;

    @Test
    void FoodRepository가_null이아님() {

        assertThat(foodRepository).isNotNull();
        assertThat(foodServiceImpl).isNotNull();
        assertThat(redisFoodRepository).isNotNull();
    }

    /**
     * TODO : 처음페이지, 마지막페이지 구분하여 하고 싶은데 하지 못함
     */
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
        GetFoodPageCondition condition = GetFoodPageCondition.builder()
                .name(searchName)
                .page(page)
                .size(size)
                .build();

        Page<GetFood> result = foodServiceImpl.getFoodPage(condition);

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
        return new BigDecimal(num).setScale(1, RoundingMode.HALF_EVEN);// 소수점 자리수 정하기
    }

    // 음식 상세보기 시작

    // 1. 음식상세보기성공_레디스에있음
    @Test
    void 음식상세보기성공_레디스에있음() {

        // given
        Long id = 1L;
        RedisFood redisFood = createRedisFood(
                id, "sdf사과asdf", 10.1, 20, 30, 50.2, 20, "A과수농장", "A과수농장", LocalDateTime.now(), LocalDateTime.now()
        );

        Mockito.doReturn(Optional.of(redisFood))
                .when(redisFoodRepository)
                .findById(id);

        // when
        GetFood result = foodServiceImpl.getFood(id);

        // then
        assertThat(result.getFoodId()).isEqualTo(id);
        assertThat(result.getKcal()).isEqualTo(createBigDecimal(50.2));


    }

    // 2. 음식상세보기실패_레디스에없음_DB에없음_진짜실패
    @Test
    void 음식상세보기실패_레디스에없음_DB에없음() {

        // given
        Long id = 1L;
        Mockito.doReturn(Optional.empty())
                .when(redisFoodRepository)
                .findById(id);

        Mockito.doReturn(Optional.empty())
                .when(foodRepository)
                .findById(id);

        // when

        FoodException foodException = Assertions.assertThrows(FoodException.class, () -> {
            foodServiceImpl.getFood(id);
        });

        // then
        assertThat(foodException.getFoodErrorCode()).isEqualTo(FoodErrorCode.FOOD_NOT_FOUND);

    }
    // 3. 음식상세보기성공_DB에있음
        // 회원에게도 보여주고
        // 레디스에도 저장한다.
    @Test
    void 음식상세보기성공_DB에있음() {

        // given
        Long id = 1L;
        FoodEntity foodEntity = createFoodEntity(
                id, "sdf사과asdf", 10.1, 20, 30, 50.2, 20, "A과수농장", "A과수농장", LocalDateTime.now(), LocalDateTime.now()
        );
        Mockito.doReturn(Optional.empty())
                .when(redisFoodRepository)
                .findById(id);

        Mockito.doReturn(Optional.of(foodEntity))
                .when(foodRepository)
                .findById(id);

        // when
        GetFood result = foodServiceImpl.getFood(id);

        // then
        assertThat(result.getFoodId()).isEqualTo(id);
        assertThat(result.getKcal()).isEqualTo(createBigDecimal(50.2));

    }



    public RedisFood createRedisFood(
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

        return RedisFood.builder()
                .foodId(foodId)
                .name(name)
                .carbo(createBigDecimal(carbo))
                .protein(createBigDecimal(protein))
                .fat(createBigDecimal(fat))
                .kcal(createBigDecimal(kcal))
                .size(createBigDecimal(size))
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .createdBy(createdBy)
                .modifiedBy(modifiedBy)
                .build();
    }

    public FoodEntity createFoodEntity(
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
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .createdBy(createdBy)
                .modifiedBy(modifiedBy)
                .build();
    }

    // 음식 상세보기 끝










}
