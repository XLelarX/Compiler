package com.lelar.instance;

import lombok.Data;

import java.util.Set;

@Data
public class SquadInstance {
    private String name;
    private Set<UserInstance> users;
}
