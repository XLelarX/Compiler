package com.lelar.database.repository;

import com.lelar.database.entity.SquadEntity;
import com.lelar.database.repository.api.SquadRepository;
import com.lelar.database.repository.api.base.InsertAndUpdateRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.lelar.database.entity.IdentifierEntity.Names.ID;
import static com.lelar.database.entity.PermissionEntity.Names.NAME;
import static com.lelar.util.QueryHelper.sendCustomQuery;

@Repository
public class SquadRepositoryImpl implements SquadRepository, InsertAndUpdateRepository<SquadEntity> {

    private static final String FIND_BY_USER_ID_SEQUENCE_PARAM = "user_id";
    private static final String FIND_BY_USER_ID_SEQUENCE = "select * from squads left join squad_bindings on squads.id = squad_bindings.squad_id where %s = ?".formatted(FIND_BY_USER_ID_SEQUENCE_PARAM);

    @Override
    public List<SquadEntity> findByUserId(Long userId) {
        RowMapper<SquadEntity> res = (resultSet, rowNum) -> {
            SquadEntity permissionEntity = new SquadEntity()
                .setName(resultSet.getString(NAME));
            permissionEntity.setId(resultSet.getLong(ID));
            return permissionEntity;
        };

        return sendCustomQuery(FIND_BY_USER_ID_SEQUENCE, Map.of(FIND_BY_USER_ID_SEQUENCE_PARAM, userId), res);
    }

}
