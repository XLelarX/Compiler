package com.lelar.service.get;

import com.lelar.service.get.api.GetService;
import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.entity.SquadEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.database.repository.api.LoginRepository;
import com.lelar.database.repository.api.PermissionRepository;
import com.lelar.database.repository.api.SquadRepository;
import com.lelar.database.repository.api.UserRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoginResponseGetService implements GetService<LoginRequest, LoginResponse> {
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final SquadRepository squadRepository;

    @Override
    public LoginResponse get(LoginRequest request) throws ApplicationException {
        LoginEntity loginEntity = loginRepository.findByLogin(request.getUsername());

        Optional<String> dbPassword = Optional.ofNullable(loginEntity).map(LoginEntity::getPassword).filter(it -> it.equals(request.getPassword()));

        if (dbPassword.isEmpty()) {
            //TODO add exception resolver in filter
            throw new WrongPasswordException();
        }

        UserEntity user = userRepository.findById(loginEntity.getId());

        List<PermissionEntity> allowedPermissionsEntity = permissionRepository.findByUserId(user.getId());
        List<Permission> allowedPermissions = mapPermissions(allowedPermissionsEntity);

        List<SquadEntity> squadEntities = squadRepository.findByUserId(user.getId());
        UserData userData = UserDataMapper.INSTANCE.map(user, squadEntities);


        return new LoginResponse()
            .setUserData(userData)
            .setPermissions(allowedPermissions);
    }

    private List<Permission> mapPermissions(List<PermissionEntity> permissions) {
        return permissions.stream()
            .map(PermissionMapper.INSTANCE::map)
            .collect(Collectors.toList());
    }
}
