package com.example.sosikfoodservice.model.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class Name {


    @Column(name = "name", nullable = false)
    private String value;

    public Name(final String value){
        validateNull(value);
        this.value = value;
    }


    private void validateNull(final String value) {
        if(Objects.isNull(value)){
            throw new NullPointerException("이름은 공백일 수 없습니다!");
        }
    }

}
