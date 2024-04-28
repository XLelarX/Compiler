package com.lelar.service.get;

import com.lelar.database.repository.LoginRepositoryImpl;
import com.lelar.database.repository.PermissionRepositoryImpl;
import com.lelar.database.repository.SquadRepositoryImpl;
import com.lelar.database.repository.UserRepositoryImpl;
import com.lelar.exception.ClientNotFoundException;
import com.lelar.service.get.api.GetService;
import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.entity.SquadEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.dto.login.LoginRequest;
import com.lelar.dto.login.LoginResponse;
import com.lelar.dto.login.Permission;
import com.lelar.dto.login.UserData;
import com.lelar.exception.ApplicationException;
import com.lelar.exception.WrongPasswordException;
import com.lelar.mapper.PermissionMapper;
import com.lelar.mapper.UserDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lelar.database.entity.LoginEntity.Names.LOGIN;
import static com.lelar.database.entity.UserEntity.Names.LOGIN_ID;

@Component
@RequiredArgsConstructor
public class LoginResponseGetService implements GetService<LoginRequest, LoginResponse> {
    private final LoginRepositoryImpl loginRepository;
    private final UserRepositoryImpl userRepository;
    private final PermissionRepositoryImpl permissionRepository;
    private final SquadRepositoryImpl squadRepository;

    @Override
    public LoginResponse get(LoginRequest request) throws ApplicationException {
        LoginEntity loginEntity = loginRepository.get(LoginEntity.class, Map.of(LOGIN, request.getUsername()));

        String dbPassword = Optional.ofNullable(loginEntity)
            .map(LoginEntity::getPassword)
            .orElseThrow(ClientNotFoundException::new);

        if (!dbPassword.equals(request.getPassword())) {
            throw new WrongPasswordException();
        }

        UserEntity user = userRepository.get(UserEntity.class, Map.of(LOGIN_ID, loginEntity.getId()));

        List<PermissionEntity> allowedPermissionsEntity = permissionRepository.findByUserId(user.getId());
        List<SquadEntity> squadEntities = squadRepository.findByUserId(user.getId());
        UserData userData = UserDataMapper.INSTANCE.map(user, squadEntities);

        return new LoginResponse()
            .setUserData(userData)
            .setPermissions(mapPermissions(allowedPermissionsEntity));
    }

    private List<Permission> mapPermissions(List<PermissionEntity> permissions) {
        return permissions.stream()
            .map(PermissionMapper.INSTANCE::map)
            .collect(Collectors.toList());
    }
}
