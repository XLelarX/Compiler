package com.lelar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("M"), FEMALE("F");

    private final String genderKey;

    public static Gender getGenderByKey(String genderKey) {
        return Arrays.stream(Gender.values())
            .filter(it -> it.getGenderKey().equals(genderKey))
            .findFirst().orElse(null);
    }
}
