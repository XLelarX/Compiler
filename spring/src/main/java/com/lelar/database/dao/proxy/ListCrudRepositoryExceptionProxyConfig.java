package com.lelar.database.dao.proxy;

import com.lelar.database.dao.LoginRepository;
import com.lelar.database.dao.PermissionRepository;
import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.SquadRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.dao.UserRepository;
import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.SquadEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.database.entity.TournamentStatusEntity;
import com.lelar.database.entity.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.ListCrudRepository;

@Configuration
public class ListCrudRepositoryExceptionProxyConfig {
    @Bean
    @Primary
    ListCrudRepository<LoginEntity, Long> loginRepositoryProxy(LoginRepository repository) {
        return new ListCrudRepositoryExceptionProxy<>(repository, LoginEntity.Names.TABLE_NAME);
    }

    @Bean
    @Primary
    ListCrudRepository<PermissionEntity, Long> permissionRepositoryProxy(PermissionRepository repository) {
        return new ListCrudRepositoryExceptionProxy<>(repository, PermissionEntity.Names.TABLE_NAME);
    }

    @Bean
    @Primary
    ListCrudRepository<PictureFormatEntity, Long> pictureFormatRepositoryProxy(PictureFormatRepository repository) {
        return new ListCrudRepositoryExceptionProxy<>(repository, PictureFormatEntity.Names.TABLE_NAME);
    }

    @Bean
    @Primary
    ListCrudRepository<PictureEntity, Long> pictureRepositoryProxy(PictureRepository repository) {
        return new ListCrudRepositoryExceptionProxy<>(repository, PictureEntity.Names.TABLE_NAME);
    }

    @Bean
    @Primary
    ListCrudRepository<SquadEntity, Long> squadRepositoryProxy(SquadRepository repository) {
        return new ListCrudRepositoryExceptionProxy<>(repository, SquadEntity.Names.TABLE_NAME);
    }

    @Bean
    @Primary
    ListCrudRepository<TournamentEntity, Long> tournamentRepositoryProxy(TournamentRepository repository) {
        return new ListCrudRepositoryExceptionProxy<>(repository, TournamentEntity.Names.TABLE_NAME);
    }

    @Bean
    @Primary
    ListCrudRepository<TournamentStatusEntity, Long> tournamentStatusRepositoryProxy(TournamentStatusRepository repository) {
        return new ListCrudRepositoryExceptionProxy<>(repository, TournamentStatusEntity.Names.TABLE_NAME);
    }

    @Bean
    @Primary
    ListCrudRepository<UserEntity, Long> userRepositoryProxy(UserRepository repository) {
        return new ListCrudRepositoryExceptionProxy<>(repository, UserEntity.Names.TABLE_NAME);
    }
}
