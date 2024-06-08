package com.lelar.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permissions {
    CLASSIC_TOURNAMENT_UPDATE("classic.tournament.update", "Обновление классических турниров", false),

    BEACH_TOURNAMENT_UPDATE("beach.tournament.update", "Обновление пляжных турниров", false),
    BEACH_TOURNAMENT_SQUAD_UPDATE("beach.tournament.squad.update", "Обновление команд для пляжных турниров", false),

    PICTURE_UPDATE("picture.update", "Обновление картинок", false);

    private String key;
    private String description;
    private boolean defaultValue;

}
