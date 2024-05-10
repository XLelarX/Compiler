package com.lelar.service.get;

import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.instance.PictureInstance;
import com.lelar.instance.TournamentInstance;
import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.tournament.get.GetTournamentRequest;
import com.lelar.dto.tournament.get.GetTournamentResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.mapper.TournamentMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ObtainTournamentProcessor implements ObtainDataProcessor<GetTournamentRequest, GetTournamentResponse> {
    private final TournamentRepository tournamentRepository;
    private final PictureRepository pictureRepository;
    private final PictureFormatRepository pictureFormatRepository;

    @Override
    public GetTournamentResponse process(GetTournamentRequest request) throws ApplicationException {
        List<TournamentEntity> tournaments = tournamentRepository.findBetween(
            request.getStartBeginDate(),
            request.getStartEndDate()
        );

        List<PictureEntity> pictures = List.of();
        List<PictureFormatEntity> formats = List.of();

        if (BooleanUtils.isTrue(request.isNeedToLoadPictures())) {
            Set<Long> pictureIds = tournaments.stream()
                .map(TournamentEntity::getTournamentPictureRefs)
                .flatMap(Collection::stream).map(it -> it.getTournamentId().getId())
                .collect(Collectors.toSet());
            pictures = pictureRepository.findAllById(pictureIds);

            Set<Long> formatIds = pictures.stream()
                .map(PictureEntity::getFormatId)
                .map(AggregateReference::getId)
                .collect(Collectors.toSet());
            formats = pictureFormatRepository.findAllById(formatIds);
        }

        Set<TournamentInstance> tournamentInstances = createInstances(tournaments, pictures, formats);

        return new GetTournamentResponse().setTournaments(tournamentInstances.stream().map(TournamentMapper.INSTANCE::map).collect(Collectors.toSet()));
    }

    private Set<TournamentInstance> createInstances(
        List<TournamentEntity> tournaments,
        List<PictureEntity> pictures,
        List<PictureFormatEntity> formats
    ) {
        Map<Long, PictureEntity> pictureWithIds = pictures.stream()
            .collect(Collectors.toMap(PictureEntity::getId, Function.identity()));
        Map<Long, PictureFormatEntity> formatWithIds = formats.stream()
            .collect(Collectors.toMap(PictureFormatEntity::getId, Function.identity()));

        Set<TournamentInstance> tournamentInstances = tournaments.stream()
            .map(TournamentMapper.INSTANCE::mapToInstance).collect(Collectors.toSet());

        tournamentInstances.forEach(
            it -> it.getPictures().forEach(
                picture -> enrichPicture(pictureWithIds, formatWithIds, picture)
            )
        );

        return tournamentInstances;
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
