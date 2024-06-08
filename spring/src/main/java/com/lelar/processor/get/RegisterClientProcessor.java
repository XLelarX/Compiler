package com.lelar.processor.get;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.database.dao.LoginRepository;
import com.lelar.database.dao.PermissionRepository;
import com.lelar.database.dao.UserRepository;
import com.lelar.dto.login.LoginResponse;
import com.lelar.dto.register.RegisterRequest;
import com.lelar.exception.ApplicationException;
import com.lelar.exception.ClientAlreadyRegistered;
import com.lelar.mapper.PermissionMapper;
import com.lelar.mapper.UserMapper;
import com.lelar.processor.get.api.ObtainDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegisterClientProcessor implements ObtainDataProcessor<RegisterRequest, LoginResponse> {
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public LoginResponse process(RegisterRequest request) throws ApplicationException {
        LoginEntity loginEntity = loginRepository.findByLogin(request.getUsername());

        if (loginEntity != null) {
            throw new ClientAlreadyRegistered();
        }

        List<PermissionEntity> permissions = permissionRepository.findAll();

        UserEntity entity = UserMapper.INSTANCE.map(request);
        entity.setPermissionUserRefs(permissions.stream().map(PermissionMapper.INSTANCE::mapRef).collect(Collectors.toSet()));

        userRepository.save(entity);

        return new LoginResponse()
            .setUser(UserMapper.INSTANCE.map(entity))
            .setPermissions(PermissionMapper.INSTANCE.mapPermissions(permissions));
    }

}
