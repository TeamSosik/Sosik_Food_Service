package com.example.sosikfoodservice.repository.redis;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.FoodRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO : 현재는 통합테스트로 진행하지만 단위테스트로 레디스를 테스트할 수 있도록 하기
 */
@SpringBootTest
public class RedisFoodRepositoryTest {

    @Autowired
    RedisFoodRepository redisFoodRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    void 음식데이터목록등록() {

        // given
        FoodEntity food1 = createFood("sdf하하asdf", 10.1, 20, 30, 50.2, 20);
        FoodEntity food2 = createFood("asfd하하", 10.1, 20, 30, 50.2, 20);
        FoodEntity food3 = createFood("하하입니다.", 10.1, 20, 30, 50.2, 20);

        foodRepository.save(food1);
        foodRepository.save(food2);
        foodRepository.save(food3);

        List<FoodEntity> foodAllList = foodRepository.findAll();

        // RedisFoodList 생성
        List<RedisFood> redisFoodList = foodAllList.stream()
                .map(RedisFood::create)
                .collect(toList());

        redisFoodRepository.saveAll(redisFoodList);

        // when
        List<RedisFood> result = redisFoodRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(3);
    }

    private FoodEntity createFood(String name,
                                  double carbo,
                                  double protein,
                                  double fat,
                                  double kcal,
                                  double size) {
        return FoodEntity.builder()
                .name(name)
                .carbo(createBigDecimal(carbo))
                .protein(createBigDecimal(protein))
                .fat(createBigDecimal(fat))
                .kcal(createBigDecimal(kcal))
                .size(createBigDecimal(size))
                .build();
    }

    private BigDecimal createBigDecimal(double num) {
        return new BigDecimal(num).setScale(1, RoundingMode.HALF_EVEN);// 소수점 자리수 정하기
    }

    @Test
    void 음식데이터존재하는지조회() {

        // given
        RedisFood redisFood = createRedisFood(
                1L,
                "sdf하하asdf",
                10.1,
                20,
                30,
                50.2,
                20);

        redisFoodRepository.save(redisFood);
        // when
        Optional<RedisFood> OptionalRedisFood = redisFoodRepository.findById(1L);

        // then
        assertThat(OptionalRedisFood.isPresent()).isTrue();
        assertThat(OptionalRedisFood.get().getFoodId()).isEqualTo(1L);
        assertThat(OptionalRedisFood.get().getCarbo()).isEqualTo(createBigDecimal(10.1));

    }

    private RedisFood createRedisFood(
            Long foodId,
            String name,
            double carbo,
            double protein,
            double fat,
            double kcal,
            double size
    ) {
        return RedisFood.builder()
                .foodId(foodId)
                .name(name)
                .carbo(createBigDecimal(carbo))
                .protein(createBigDecimal(protein))
                .fat(createBigDecimal(fat))
                .kcal(createBigDecimal(kcal))
                .size(createBigDecimal(size))
                .build();
    }








}
