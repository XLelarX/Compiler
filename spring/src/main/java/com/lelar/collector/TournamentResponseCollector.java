package com.lelar.collector;

import com.lelar.collector.api.Collector;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.database.repository.api.TournamentRepository;
import com.lelar.dto.tournament.Tournament;
import com.lelar.dto.tournament.TournamentRequest;
import com.lelar.dto.tournament.TournamentResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.mapper.TournamentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TournamentResponseCollector implements Collector<TournamentRequest, TournamentResponse> {
    private final TournamentRepository tournamentRepository;

    @Override
    public TournamentResponse collect(TournamentRequest request) throws ApplicationException {

        List<TournamentEntity> tournaments = tournamentRepository.findBetween(request.getStartBeginDate(), request.getStartEndDate());

        return new TournamentResponse()
            .setTournaments(mapPermissions(tournaments));
    }

    private List<Tournament> mapPermissions(List<TournamentEntity> tournaments) {
        return tournaments.stream()
            .map(TournamentMapper.INSTANCE::map)
            .collect(Collectors.toList());
    }
}
