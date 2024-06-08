package com.lelar.processor.get;

import com.lelar.database.dao.SquadRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.entity.SquadEntity;
import com.lelar.database.entity.TournamentStatusEntity;
import com.lelar.exception.RecordInDbNotFoundException;
import com.lelar.instance.SquadInstance;
import com.lelar.instance.TournamentInstance;
import com.lelar.mapper.SquadMapper;
import com.lelar.processor.get.api.ObtainDataProcessor;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.tournament.GetTournamentRequest;
import com.lelar.dto.tournament.beach.get.GetBeachTournamentResponse;
import com.lelar.exception.ApplicationException;
import com.lelar.mapper.TournamentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ObtainBeachTournamentProcessor implements ObtainDataProcessor<GetTournamentRequest, GetBeachTournamentResponse> {
    private final TournamentRepository tournamentRepository;
    private final SquadRepository squadRepository;
    private final TournamentStatusRepository statusRepository;

    @Override
    public GetBeachTournamentResponse process(GetTournamentRequest request) throws ApplicationException {
        List<TournamentEntity> tournaments = tournamentRepository.findByDateAndGender(
            request.getStartBeginDate(),
            request.getStartEndDate(),
            request.getGender().getGenderKey()
        );

        Set<TournamentInstance> tournamentInstances = createInstances(tournaments);

        return new GetBeachTournamentResponse().setTournaments(
            tournamentInstances.stream()
                .map(TournamentMapper.INSTANCE::map)
                .collect(Collectors.toSet())
        );
    }

    private Set<TournamentInstance> createInstances(
        List<TournamentEntity> tournaments
    ) throws RecordInDbNotFoundException {
        Set<TournamentInstance> instances = new HashSet<>();

        for (TournamentEntity entity : tournaments) {
            TournamentInstance instance = TournamentMapper.INSTANCE.mapToInstance(entity);

            SquadInstance squadInstance = createSquadInstance(entity.getSquadId());
            SquadInstance opponentSquadInstance = createSquadInstance(entity.getOpponentsSquadId());
            TournamentStatusEntity statusEntity = statusRepository.findById(entity.getSquadId().getId())
                .orElseThrow(() -> RecordInDbNotFoundException.of(TournamentStatusEntity.Names.TABLE_NAME));

            instance.setStatus(statusEntity.getStatusKey());
            instance.setSquadInstance(squadInstance);
            instance.setOpponentsSquadInstance(opponentSquadInstance);
            instances.add(instance);
        }

        return instances;
    }

    private SquadInstance createSquadInstance(AggregateReference<SquadEntity, Long> squadId) throws RecordInDbNotFoundException {
        SquadEntity squad = squadRepository.findById(squadId.getId())
            .orElseThrow(() -> RecordInDbNotFoundException.of(SquadEntity.Names.TABLE_NAME));

        return SquadMapper.INSTANCE.mapToInstance(squad);
    }

}
