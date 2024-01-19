package com.example.sosikfoodservice.service;

import com.example.sosikfoodservice.dto.request.GetFoodPageCondition;
import com.example.sosikfoodservice.dto.response.ResponseGetFood;
import com.example.sosikfoodservice.exception.FoodErrorCode;
import com.example.sosikfoodservice.exception.FoodException;
import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.FoodRepository;
import com.example.sosikfoodservice.repository.redis.RedisFood;
import com.example.sosikfoodservice.repository.redis.RedisFoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final RedisFoodRepository redisFoodRepository;

    @Override
    public Page<ResponseGetFood> getFoodPage(GetFoodPageCondition condition) {

        // Pageable 만들기
        int realPage = 0;
        if (condition.getPage() != 0) {
            realPage = condition.getPage() - 1;
        }
        Pageable pageable = createPage(realPage, condition.getSize());

        // repository에서 Page 불러오기
        Page<FoodEntity> pageFoodList = foodRepository.findPageByNameContainingOrderByNameDesc(condition.getName(), pageable);

        // dto Page로 만들기

        return pageFoodList.map(ResponseGetFood::create);
    }

    private Pageable createPage(int page, int size) {

        return PageRequest.of(page, size);
    }

    @Override
    public ResponseGetFood getFood(Long id) {

        // 레디스에있으면 DTO 만들어서 바로 리턴
        Optional<RedisFood> OptionalRedisFood = redisFoodRepository.findById(id);

        if (OptionalRedisFood.isPresent()) {
            RedisFood redisFood = OptionalRedisFood.get();
            return ResponseGetFood.create(redisFood);
        }

        Optional<FoodEntity> optionalFood = foodRepository.findById(id);

        if (optionalFood.isEmpty()) {
            throw new FoodException(FoodErrorCode.FOOD_NOT_FOUND);
        }
        FoodEntity food = optionalFood.get();

        // 레디스에도 저장한다.
        RedisFood redisFood = RedisFood.create(food);
        redisFoodRepository.save(redisFood);

        // 회원에게도 보여준다.
        return ResponseGetFood.create(food);
    }

    @Override
    public List<ResponseGetFood> getFoodName(String inputValue) {
        try {
            return foodRepository.find10FoodBySearch(inputValue)
                    .stream()
                    .map(ResponseGetFood::create)
                    .collect(Collectors.toList());
        } catch (RuntimeException ignored) {

        }
        return null;
    }
}
