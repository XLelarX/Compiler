package com.lelar.database.repository;

import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.repository.api.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PictureRepositoryImpl implements PictureRepository {

    private final JdbcTemplate jdbcTemplate;

    private PictureEntity mapRowToDataEntity(ResultSet resultSet, int rowNum) throws SQLException {
        return new PictureEntity()
            .setLocalPath(resultSet.getString("local_path"))
            .setServerPath(resultSet.getString("server_path"))
            .setFormat(resultSet.getString("format"));
    }

    @Override
    public List<PictureEntity> findByTournamentId(Long tournamentId) {
        return jdbcTemplate.query("select server_path, local_path, format from picture_bindings left join pictures on picture_bindings.picture_id=pictures.id left join picture_formats on picture_formats.id=pictures.id where tournament_id = ?", this::mapRowToDataEntity, tournamentId);
    }

}
