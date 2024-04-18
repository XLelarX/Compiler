package com.lelar.session;

import com.lelar.dto.login.Permission;
import com.lelar.dto.login.UserData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SessionData {
    private UserData userData;
    private List<Permission> permissions;
}
