package com.lelar.service.get;

import com.lelar.database.dao.ClassicTournamentRepository;
import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.entity.ClassicTournamentEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.TournamentStatusEntity;
import com.lelar.dto.tournament.classic.get.GetClassicTournamentResponse;
import com.lelar.dto.tournament.get.GetTournamentRequest;
import com.lelar.exception.ApplicationException;
import com.lelar.exception.RecordInDbNotFoundException;
import com.lelar.instance.PictureInstance;
import com.lelar.instance.TournamentInstance;
import com.lelar.mapper.TournamentMapper;
import com.lelar.service.get.api.ObtainDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ObtainClassicTournamentProcessor implements ObtainDataProcessor<GetTournamentRequest, GetClassicTournamentResponse> {
    private final ClassicTournamentRepository tournamentRepository;
    private final PictureRepository pictureRepository;
    private final PictureFormatRepository pictureFormatRepository;
    private final TournamentStatusRepository statusRepository;

    @Override
    public GetClassicTournamentResponse process(GetTournamentRequest request) throws ApplicationException {
        List<ClassicTournamentEntity> tournaments = tournamentRepository.findByDateAndGender(
            request.getStartBeginDate(),
            request.getStartEndDate(),
            request.getGender().getGenderKey()
        );

        List<TournamentInstance> instances = new ArrayList<>();

        tournaments.forEach(
            tournament -> {
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
                    tournament, pictures, formats
                );
                instances.add(tournamentInstance);
            }
        );

        return new GetClassicTournamentResponse().setTournaments(
            instances.stream()
                .map(TournamentMapper.INSTANCE::mapClassic)
                .collect(Collectors.toSet())
        );
    }

    private TournamentInstance createTournamentInstance(
        ClassicTournamentEntity tournament,
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

}
