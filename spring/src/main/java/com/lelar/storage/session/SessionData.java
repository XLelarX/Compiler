package com.lelar.storage.session;

import com.lelar.dto.login.Permission;
import com.lelar.dto.login.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SessionData {
    private User userData;
    private List<Permission> permissions;
}
