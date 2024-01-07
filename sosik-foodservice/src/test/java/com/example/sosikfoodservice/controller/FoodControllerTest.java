package com.example.sosikfoodservice.controller;

import com.example.sosikfoodservice.dto.response.GetFood;
import com.example.sosikfoodservice.dto.request.GetFoodPageCondition;
import com.example.sosikfoodservice.exception.FoodErrorCode;
import com.example.sosikfoodservice.exception.FoodException;
import com.example.sosikfoodservice.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FoodControllerTest {

    @InjectMocks
    FoodController foodController;
    @Mock
    FoodService foodService;
    MockMvc mockMvc;


    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodController)
                .setControllerAdvice(new FoodControllerAdvice())
                .build();
    }
    @Test
    void Mock객체중Null이없음() {

        mockMvc = MockMvcBuilders.standaloneSetup(foodController)
                .build();

        assertThat(foodController).isNotNull();
        assertThat(foodService).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @ParameterizedTest
    @MethodSource(value = "tsetFailParameters")
    void 임식목록조회실패_page가없을때(
            Integer page,
            Integer size,
            String errorField,
            String errorCode,
            String errorMessage
    ) throws Exception {

        String url = "/food/v1";
        String searchName = "sdf사과asdf";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if(Objects.isNull(page)) {
            params.add("name", searchName);
            params.add("size", size + "");
        }
        if(Objects.isNull(size)) {
            params.add("name", searchName);
            params.add("page", page + "");
        }
        if(Objects.nonNull(page) && Objects.nonNull(size)) {
            params.add("name", searchName);
            params.add("page", page + "");
            params.add("size", size + "");
        }
        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.field").value(errorField));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(errorCode));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorMessage));


    }

    private static Stream<Arguments> tsetFailParameters() {

        return Stream.of(
                Arguments.of(null, 12, "page", HttpStatus.BAD_REQUEST.name(), "유효하지 않은 값입니다."),//page가 null일 때
                Arguments.of(2, null, "size", HttpStatus.BAD_REQUEST.name(), "유효하지 않은 값입니다."),// size가 null일 때
                Arguments.of(-1, 13, "page", HttpStatus.BAD_REQUEST.name(), "음수일 수 없습니다.")// page가 음수일 때
        );
    }

    @Test
    void 음식목록조회성공() throws Exception {

        // given
        int total = 121;
        List<GetFood> foodList = new ArrayList<>();

        for(long i = 0; i < total; i++) {

            GetFood food = null;

            if(i % 3 == 0) {
                food = createGetFood(i, "sdf사과asdf", 10.1, 20, 30, 50.2, 20, "A과수농장", "A과수농장", LocalDateTime.now(), LocalDateTime.now());
            } else if(i % 3 == 1)  {
                food = createGetFood(i,"ok과", 10.1, 20, 30, 50.2, 20, "B과수농장", "B과수농장", LocalDateTime.now(), LocalDateTime.now());
            } else {
                food = createGetFood(i,"과asdf", 10.1, 20, 30, 50.2, 20, "C과수농장", "C과수농장", LocalDateTime.now(), LocalDateTime.now());
            }
            foodList.add(food);
        }

        String searchName = "sdf사과asdf";
        int page = 2;
        int size = 25;


        List<GetFood> searchGetFoodList = foodList.stream()
                .filter((food) -> {
                    return food.getName().equals(searchName);
                }).collect(toList());

        Page<GetFood> pageGetFood = new PageImpl<>(searchGetFoodList);

        BDDMockito.given(foodService.getFoodPage(BDDMockito.any(GetFoodPageCondition.class)))
                .willReturn(pageGetFood);

        // when
        String url = "/food/v1";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", searchName);
        params.add("page", page + "");
        params.add("size", size + "");

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.result.totalElements").value(searchGetFoodList.size()));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value("데이터 전송에 성공하였습니다!"));

    }

    private GetFood createGetFood(
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
        return GetFood.builder()
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


    // 음식 상세보기 시작
    // 1. 음식상세보기실패_id가없음
    @Test
    void 음식상세보기실패_id가음수일때() throws Exception {

        // given

        // when
        Long id = -1L;
        String url = "/food/v1/" + id;

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.BAD_REQUEST.name()));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("유효하지 않은 값입니다."));

    }


    // 2. 음식상세보기실패_service에서예외발생
    @Test
    void 음식상세보기실패_service에서예외발생() throws Exception {

        // given
        Long id = 20L;
        String url = "/food/v1/" + id;

        Mockito.doThrow(new FoodException(FoodErrorCode.FOOD_NOT_FOUND))
                .when(foodService)
                .getFood(id);
        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.NOT_FOUND.name()));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.message")
                .value("음식데이터를 찾지 못했습니다.")
        );

    }

    // 3. 상세보기성공
    @Test
    void 음식상세보기성공() throws Exception {

        // given
        Long id = 3L;
        String url = "/food/v1/" + id;

        Mockito.doReturn(
                createGetFood(id, "sdf사과asdf", 10.1, 20, 30, 50.2, 20, "A과수농장", "A과수농장", LocalDateTime.now(), LocalDateTime.now())
                )
                .when(foodService)
                .getFood(id);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.result.foodId").value(id));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.result.name")
                .value("sdf사과asdf")
        );



    }



    // 음식 상세보기 끝







}
