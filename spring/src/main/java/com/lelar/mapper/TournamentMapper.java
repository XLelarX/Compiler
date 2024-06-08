package com.lelar.mapper;

import com.lelar.database.entity.ClassicPictureBindingEntity;
import com.lelar.database.entity.ClassicTournamentEntity;
import com.lelar.database.entity.PictureBindingEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.picture.Picture;
import com.lelar.dto.tournament.Tournament;
import com.lelar.dto.tournament.classic.get.ClassicTournament;
import com.lelar.dto.tournament.classic.update.UpdateClassicTournamentRequest;
import com.lelar.dto.tournament.get.GetTournamentDetailResponse;
import com.lelar.dto.tournament.update.UpdateBeachTournamentRequest;
import com.lelar.instance.PictureInstance;
import com.lelar.instance.TournamentInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface TournamentMapper extends CommonMapper {
    TournamentMapper INSTANCE = Mappers.getMapper(TournamentMapper.class);

    default Set<Tournament> map(List<TournamentEntity> entity) {
        return entity.stream()
            .map(this::map)
            .collect(Collectors.toSet());
    }

    Tournament map(TournamentEntity entity);

    TournamentEntity map(UpdateBeachTournamentRequest request);

    ClassicTournamentEntity mapClassic(UpdateClassicTournamentRequest request);

    Picture map(PictureEntity entity);

    @Mapping(target = "pictures", source = "tournamentPictureRefs")
    TournamentInstance mapToInstance(TournamentEntity entity);

    @Mapping(target = "pictures", source = "tournamentPictureRefs")
    @Mapping(target = "squadInstance.name", source = "squadName")
    @Mapping(target = "opponentsSquadInstance.name", source = "opponentsSquadName")
    TournamentInstance mapToInstance(ClassicTournamentEntity entity);

    @Mapping(target = "squadName", source = "squadInstance.name")
    @Mapping(target = "opponentsSquadName", source = "opponentsSquadInstance.name")
    ClassicTournament mapClassic(TournamentInstance entity);

    default PictureInstance mapPictures(PictureBindingEntity pictureBindingEntity) {
        PictureInstance pictureInstance = new PictureInstance();
        pictureInstance.setId(pictureBindingEntity.getPictureId().getId());
        return pictureInstance;
    }

    default PictureInstance mapPictures(ClassicPictureBindingEntity pictureBindingEntity) {
        PictureInstance pictureInstance = new PictureInstance();
        pictureInstance.setId(pictureBindingEntity.getPictureId().getId());
        return pictureInstance;
    }

    @Mapping(target = "squadDetail", source = "squadInstance")
    @Mapping(target = "opponentSquadDetail", source = "opponentsSquadInstance")
    GetTournamentDetailResponse mapDetails(TournamentInstance instance);

    @Mapping(target = "squadName", source = "squadInstance.name")
    @Mapping(target = "opponentsSquadName", source = "opponentsSquadInstance.name")
    Tournament map(TournamentInstance instance);

}
