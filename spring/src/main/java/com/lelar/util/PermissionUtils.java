package com.lelar.util;

import com.lelar.dto.login.Permission;
import com.lelar.exception.OperationNotAllowedException;
import com.lelar.storage.session.SessionData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PermissionUtils {

    private final String CLIENT_DOESNT_HAVE_PERMISSION_ERROR_MESSAGE = "Client doesnt have permission: %s";

    public void checkPermission(SessionData sessionData, Permissions permission) {
        sessionData.getPermissions().stream()
            .filter(it -> it.getPermissionKey().equals(permission.getKey()))
            .findFirst()
            .filter(Permission::getAllowed)
            .orElseThrow(() -> OperationNotAllowedException.of(CLIENT_DOESNT_HAVE_PERMISSION_ERROR_MESSAGE.formatted(permission.getDescription())));
    }
}
