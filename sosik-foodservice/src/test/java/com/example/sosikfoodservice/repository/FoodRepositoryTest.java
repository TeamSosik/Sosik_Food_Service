package com.example.sosikfoodservice.repository;

import com.example.sosikfoodservice.config.TestQuerdyslConfig;
import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.model.entity.vo.Name;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(value = {TestQuerdyslConfig.class})
public class FoodRepositoryTest {


    @Autowired
    FoodRepository foodRepository;

    @Test
    void FoodRepository가null이아님() {

        assertThat(foodRepository).isNotNull();
    }

    // 음식조회_사이즈가0
    @Test
    void 음식조회_사이즈가0() {

        // given

        // when
        int page = 1;
        int size = 10;
//        Sort sort = Sort.by(Sort.Direction.ASC);
        Pageable pageable = PageRequest.of(page, size);
        String searchName = "하하";
        Page<FoodEntity> result = foodRepository.findPageByNameContainingOrderByNameDesc(searchName, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(0);

    }

    // 음식조회_사이즈가2
    @Test
    void 음식조회_사이즈가3() {
        
        // given
        // 2개 저장

        FoodEntity food1 = createFood("sdf하하asdf", 10.1, 20, 30, 50.2, 20);
        FoodEntity food2 = createFood("asfd하하", 10.1, 20, 30, 50.2, 20);
        FoodEntity food3 = createFood("하하입니다.", 10.1, 20, 30, 50.2, 20);

        foodRepository.save(food1);
        foodRepository.save(food2);
        foodRepository.save(food3);

        // when
        Pageable pageable = createPage(0, 10);

        String searchName = "하하";
        Page<FoodEntity> result = foodRepository.findPageByNameContainingOrderByNameDesc(searchName, pageable);
        
        // then
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(1);
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
        return new BigDecimal(num);
    }

    @ParameterizedTest
    @MethodSource(value = "testPageParams")
    void 음식조회_검색어에따른_totalsize확인_및_페이지확인(
            String searchName,
            int total,
            int page,// 0 == 1페이지
            int size,
            int totalElements,
            int totalPage,
            int contentSize
    ) {

        // given
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        
        for(int i = 0; i < total; i++) {
            
            FoodEntity food = null;
            
            if(i % 3 == 0) {
                food = createFood("sdf사과asdf", 10.1, 20, 30, 50.2, 20);
                count1 += 1;
            } else if(i % 3 == 1)  {
                food = createFood("asfd과", 10.1, 20, 30, 50.2, 20);
                count2 += 1;
            } else {
                food = createFood("과입니다.", 10.1, 20, 30, 50.2, 20);
                count3 += 1;
            }
            
            foodRepository.save(food);
        }

        // when
        Pageable pageable = createPage(page, size);
        Page<FoodEntity> result = foodRepository.findPageByNameContainingOrderByNameDesc(searchName, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(totalElements);
        assertThat(result.getTotalPages()).isEqualTo(totalPage);
        assertThat(result.getContent().size()).isEqualTo(contentSize);
    }

    private Pageable createPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return pageable;
    }




    private static Stream<Arguments> testPageParams() {

        return Stream.of(
                Arguments.of("사과", 121, 0, 10, 41, 5, 10),// 음식조회_검색_*사과*만검색_사이즈가_41_첫페이지
                Arguments.of("사과", 121, 4, 10, 41, 5, 1),// 음식조회_검색_*사과*만검색_사이즈가_41_마지막페이지
                Arguments.of("과", 121, 0, 10, 121, 13, 10),// 음식조회_검색_*과*만검색_사이즈가_121_첫페이지
                Arguments.of("과", 121, 12, 10, 121, 13, 1)// 음식조회_검색_*과*만검색_사이즈가_121_마지막페이지

        );
    }


}
