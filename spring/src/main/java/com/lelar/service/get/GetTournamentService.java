package com.lelar.service.get;

import com.lelar.service.get.api.GetService;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.database.repository.api.PictureRepository;
import com.lelar.database.repository.api.TournamentRepository;
import com.lelar.dto.tournament.Tournament;
import com.lelar.dto.tournament.get.GetTournamentRequest;
import com.lelar.dto.tournament.get.GetTournamentResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.mapper.TournamentMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetTournamentService implements GetService<GetTournamentRequest, GetTournamentResponse> {
    private final TournamentRepository tournamentRepository;
    private final PictureRepository pictureRepository;

    @Override
    public GetTournamentResponse get(GetTournamentRequest request) throws ApplicationException {
        List<TournamentEntity> tournaments = tournamentRepository.findBetween(request.getStartBeginDate(), request.getStartEndDate());

        Map<Long, List<PictureEntity>> pictures = new HashMap<>();
        if (BooleanUtils.isTrue(request.isNeedToLoadPictures())) {
            //TODO rework mechanism on list of ids
            pictures = tournaments.stream()
                .map(it -> Pair.of(it.getId(), pictureRepository.findByTournamentId(it.getId())))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        }

        return new GetTournamentResponse()
            .setTournaments(mapPermissions(tournaments, pictures));
    }

    private List<Tournament> mapPermissions(List<TournamentEntity> tournaments, Map<Long, List<PictureEntity>> pictures) {
        return tournaments.stream()
            .map(it -> TournamentMapper.INSTANCE.map(it, pictures.getOrDefault(it.getId(), Collections.emptyList())))
            .collect(Collectors.toList());
    }
}
