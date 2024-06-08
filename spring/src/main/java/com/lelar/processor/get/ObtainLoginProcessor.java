package com.lelar.processor.get;

import com.lelar.database.entity.PermissionBindingEntity;
import com.lelar.database.entity.SquadEntity;
import com.lelar.database.dao.LoginRepository;
import com.lelar.database.dao.PermissionRepository;
import com.lelar.database.dao.SquadRepository;
import com.lelar.database.dao.UserRepository;
import com.lelar.exception.RecordInDbNotFoundException;
import com.lelar.mapper.SquadMapper;
import com.lelar.processor.get.api.ObtainDataProcessor;
import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.dto.login.LoginRequest;
import com.lelar.dto.login.LoginResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.exception.WrongPasswordException;
import com.lelar.mapper.PermissionMapper;
import com.lelar.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObtainLoginProcessor implements ObtainDataProcessor<LoginRequest, LoginResponse> {
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final SquadRepository squadRepository;

    @Override
    public LoginResponse process(LoginRequest request) throws ApplicationException {
        //TODO Request validation

        LoginEntity loginEntity = loginRepository.findByLogin(request.getUsername());

        String dbPassword = Optional.ofNullable(loginEntity)
            .map(LoginEntity::getPassword)
            .orElseThrow(() -> RecordInDbNotFoundException.of(LoginEntity.Names.TABLE_NAME));

        if (!dbPassword.equals(request.getPassword())) {
            throw new WrongPasswordException();
        }

        UserEntity user = userRepository.findById(loginEntity.getUserId().getId())
            .orElseThrow(() -> RecordInDbNotFoundException.of(UserEntity.Names.TABLE_NAME));

        Map<Long, Boolean> permissionsAdditionalData = user.getPermissionUserRefs().stream()
            .collect(Collectors.toMap(it -> it.getPermissionId().getId(), PermissionBindingEntity::isAllowed));

        List<PermissionEntity> permissions = permissionRepository.findAllById(permissionsAdditionalData.keySet());

        List<SquadEntity> squads = squadRepository.findAllById(
            user.getSquadUserRefs().stream().map(it -> it.getSquadId().getId()).collect(Collectors.toList())
        );

        return new LoginResponse()
            .setUser(UserMapper.INSTANCE.map(user))
            .setPermissions(PermissionMapper.INSTANCE.mapPermissions(permissions, permissionsAdditionalData))
            .setSquads(SquadMapper.INSTANCE.mapSquads(squads));
    }

}
