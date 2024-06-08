package com.lelar.service.get;

import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.SquadRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.dao.UserRepository;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.SquadBindingEntity;
import com.lelar.database.entity.SquadEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.database.entity.TournamentStatusEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.dto.tournament.get.GetTournamentDetailResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.exception.RecordInDbNotFoundException;
import com.lelar.instance.PictureInstance;
import com.lelar.instance.SquadInstance;
import com.lelar.instance.TournamentInstance;
import com.lelar.mapper.SquadMapper;
import com.lelar.mapper.TournamentMapper;
import com.lelar.mapper.UserMapper;
import com.lelar.service.get.api.ObtainDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ObtainBeachTournamentDetailsProcessor implements ObtainDataProcessor<Long, GetTournamentDetailResponse> {
    private final TournamentRepository tournamentRepository;
    private final PictureRepository pictureRepository;
    private final PictureFormatRepository pictureFormatRepository;
    private final SquadRepository squadRepository;
    private final UserRepository userRepository;
    private final TournamentStatusRepository statusRepository;

    @Override
    public GetTournamentDetailResponse process(Long request) throws ApplicationException {
        TournamentEntity tournament = tournamentRepository.findById(request)
            .orElseThrow(() -> RecordInDbNotFoundException.of(TournamentEntity.Names.TABLE_NAME));

        SquadInstance squadInstance = createSquadInstance(tournament.getSquadId());
        SquadInstance opponentSquadInstance = createSquadInstance(tournament.getOpponentsSquadId());

        Set<Long> pictureIds = tournament.getTournamentPictureRefs().stream()
            .map(it -> it.getTournamentId().getId())
            .collect(Collectors.toSet());

        List<PictureEntity> pictures = pictureRepository.findAllById(pictureIds);

        Set<Long> formatIds = pictures.stream()
            .map(PictureEntity::getFormatId)
            .map(AggregateReference::getId)
            .collect(Collectors.toSet());
        List<PictureFormatEntity> formats = pictureFormatRepository.findAllById(formatIds);

        TournamentInstance tournamentInstance = createTournamentInstance(
            tournament, squadInstance, opponentSquadInstance, pictures, formats
        );

        return TournamentMapper.INSTANCE.mapDetails(tournamentInstance);
    }

    private TournamentInstance createTournamentInstance(
        TournamentEntity tournament,
        SquadInstance squadInstance,
        SquadInstance opponentSquadInstance,
        List<PictureEntity> pictures,
        List<PictureFormatEntity> formats
    ) {
        Map<Long, PictureEntity> pictureWithIds = pictures.stream()
            .collect(Collectors.toMap(PictureEntity::getId, Function.identity()));
        Map<Long, PictureFormatEntity> formatWithIds = formats.stream()
            .collect(Collectors.toMap(PictureFormatEntity::getId, Function.identity()));

        TournamentInstance tournamentInstance = TournamentMapper.INSTANCE.mapToInstance(tournament);

        tournamentInstance.getPictures().forEach(
            picture -> enrichPicture(pictureWithIds, formatWithIds, picture)
        );
        TournamentStatusEntity statusEntity = statusRepository.findById(tournament.getStatusId().getId())
            .orElseThrow(() -> RecordInDbNotFoundException.of(TournamentStatusEntity.Names.TABLE_NAME));

        tournamentInstance.setStatus(statusEntity.getStatusKey());
        tournamentInstance.setSquadInstance(squadInstance);
        tournamentInstance.setOpponentsSquadInstance(opponentSquadInstance);

        return tournamentInstance;
    }

    private void enrichPicture(
        Map<Long, PictureEntity> pictureWithIds,
        Map<Long, PictureFormatEntity> formatWithIds,
        PictureInstance picture
    ) {
        PictureEntity entity = pictureWithIds.get(picture.getId());

        picture.setId(entity.getId());
        picture.setLocalPath(entity.getLocalPath());
        picture.setServerPath(entity.getServerPath());
        picture.setFormat(formatWithIds.get(picture.getId()).getFormat());
    }

    private SquadInstance createSquadInstance(AggregateReference<SquadEntity, Long> squadId) throws RecordInDbNotFoundException {
        SquadEntity squad = squadRepository.findById(squadId.getId())
            .orElseThrow(() -> RecordInDbNotFoundException.of(SquadEntity.Names.TABLE_NAME));

        List<UserEntity> users = userRepository.findAllById(
            squad.getSquadUserRefs().stream()
                .map(SquadBindingEntity::getUserId)
                .map(AggregateReference::getId)
                .collect(Collectors.toList())
        );

        SquadInstance squadInstance = SquadMapper.INSTANCE.mapToInstance(squad);
        squadInstance.setUsers(users.stream().map(UserMapper.INSTANCE::mapToInstance).collect(Collectors.toSet()));

        return squadInstance;
    }

}
