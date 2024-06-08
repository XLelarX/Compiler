package com.lelar.storage.session;

import com.lelar.dto.login.Permission;
import com.lelar.dto.login.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class SessionData {
    private long ttl;
    private User userData;
    private Set<Permission> permissions;
}
