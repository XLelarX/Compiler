package com.lelar.dto.tournament.beach.get;

import com.lelar.dto.login.User;
import lombok.Data;

import java.util.Set;

@Data
public class SquadDetail {
    private String name;
    private Set<User> users;
}
