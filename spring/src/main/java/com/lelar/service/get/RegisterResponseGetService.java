package com.lelar.service.get;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.database.repository.LoginRepositoryImpl;
import com.lelar.database.repository.PermissionRepositoryImpl;
import com.lelar.database.repository.UserRepositoryImpl;
import com.lelar.database.repository.api.SquadRepository;
import com.lelar.dto.login.LoginResponse;
import com.lelar.dto.login.Permission;
import com.lelar.dto.login.RegisterRequest;
import com.lelar.dto.login.UserData;
import com.lelar.exception.ApplicationException;
import com.lelar.exception.ClientAlreadyRegistered;
import com.lelar.exception.WrongPasswordException;
import com.lelar.mapper.PermissionMapper;
import com.lelar.mapper.UserDataMapper;
import com.lelar.service.get.api.GetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lelar.database.entity.LoginEntity.Names.LOGIN;

@Component
@RequiredArgsConstructor
public class RegisterResponseGetService implements GetService<RegisterRequest, LoginResponse> {
    private final LoginRepositoryImpl loginRepository;
    private final UserRepositoryImpl userRepository;
    private final PermissionRepositoryImpl permissionRepository;
    private final SquadRepository squadRepository;

    @Override
    public LoginResponse get(RegisterRequest request) throws ApplicationException {
        LoginEntity loginEntity = loginRepository.get(LoginEntity.class, Map.of(LOGIN, request.getUsername()));

        if (loginEntity != null) {
            throw new ClientAlreadyRegistered();
        }

        Long loginId = loginRepository.insert(new LoginEntity().setLogin(request.getUsername()).setPassword(request.getPassword()));

        Long userId = userRepository.insert(new UserEntity().setFirstName(request.getFirstName()).setSecondName(request.getSecondName()).setPatronymic(request.getPatronymic()).setLoginId(loginId));

        List<PermissionEntity> permissionEntities = permissionRepository.get(PermissionEntity.class, null);
        System.out.println(permissionEntities);

        permissionEntities.stream().forEach(
            it -> permissionRepository.insertIntoBindingTable(it.getId(), userId, it.isDefaultValue())
        );

        UserData userData = UserDataMapper.INSTANCE.map(request);

        return new LoginResponse()
            .setUserData(userData)
            .setPermissions(mapPermissions(permissionEntities));
    }

    private List<Permission> mapPermissions(List<PermissionEntity> permissions) {
        return permissions.stream()
            .map(PermissionMapper.INSTANCE::map)
            .collect(Collectors.toList());
    }

}
