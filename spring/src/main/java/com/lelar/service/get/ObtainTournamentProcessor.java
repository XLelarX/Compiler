package com.lelar.service.get;

import com.lelar.database.entity.PictureBindingEntity;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.service.get.api.ObtainDataProcessor;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.tournament.get.GetTournamentRequest;
import com.lelar.dto.tournament.get.GetTournamentResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.mapper.TournamentMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.Pair;
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

    @Override
    public GetTournamentResponse process(GetTournamentRequest request) throws ApplicationException {
        List<TournamentEntity> tournaments = tournamentRepository.findBetween(
            request.getStartBeginDate(),
            request.getStartEndDate()
        );

        Map<TournamentEntity, Set<PictureEntity>> tournamentToPictures = Map.of();

        if (BooleanUtils.isTrue(request.isNeedToLoadPictures())) {
            Map<TournamentEntity, Set<Long>> tournamentsAndPictures = tournaments.stream()
                .collect(Collectors.toMap(Function.identity(), it -> it.getTournamentPictureRefs().stream()
                    .map(PictureBindingEntity::getPictureId)
                    .map(AggregateReference::getId).collect(Collectors.toSet())));

            Set<Long> pictureIds = tournamentsAndPictures.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

            Map<Long, PictureEntity> pictures = pictureRepository.findAllById(pictureIds).stream()
                .collect(Collectors.toMap(PictureEntity::getId, Function.identity()));

            tournamentToPictures = tournamentsAndPictures.entrySet().stream()
                .map(it -> Pair.of(it.getKey(), it.getValue().stream().map(pictures::get).collect(Collectors.toSet())))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
        }

        return new GetTournamentResponse()
            .setTournaments(TournamentMapper.INSTANCE.map(tournaments, tournamentToPictures));
    }

}
