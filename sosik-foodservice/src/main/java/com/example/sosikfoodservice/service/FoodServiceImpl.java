package com.example.sosikfoodservice.service;

import com.example.sosikfoodservice.dto.response.GetFood;
import com.example.sosikfoodservice.dto.request.GetFoodPageCondition;
import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Override
    public Page<GetFood> getFoodPage(GetFoodPageCondition condition) {

        // Pageable 만들기
        int realPage = 0;
        if(condition.getPage() != 0) {
            realPage = condition.getPage() - 1;
        }
        Pageable pageable = createPage(realPage, condition.getSize());

        // repository에서 Page 불러오기
        Page<FoodEntity> pageFoodList = foodRepository.findPageByNameContainingOrderByNameDesc(condition.getName(), pageable);

        // dto Page로 만들기
        Page<GetFood> pageGetFood = pageFoodList.map(GetFood::create);

        return pageGetFood;
    }

    private Pageable createPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return pageable;
    }
}
