package com.lelar.processor.get;

import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.SquadRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.dao.UserRepository;
import com.lelar.database.entity.PictureBindingEntity;
import com.lelar.database.entity.SquadBindingEntity;
import com.lelar.database.entity.SquadEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.dto.tournament.beach.get.GetTournamentDetailResponse;
import com.lelar.exception.RecordInDbNotFoundException;
import com.lelar.instance.SquadInstance;
import com.lelar.instance.TournamentInstance;
import com.lelar.mapper.SquadMapper;
import com.lelar.mapper.TournamentMapper;
import com.lelar.mapper.UserMapper;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObtainBeachTournamentDetailsProcessor extends ObtainCommonTournamentProcessor<TournamentEntity, PictureBindingEntity, Long, GetTournamentDetailResponse> {
    private final TournamentRepository tournamentRepository;
    private final SquadRepository squadRepository;
    private final UserRepository userRepository;

    public ObtainBeachTournamentDetailsProcessor(
        PictureRepository pictureRepository,
        PictureFormatRepository pictureFormatRepository,
        TournamentStatusRepository statusRepository,
        TournamentRepository tournamentRepository,
        SquadRepository squadRepository,
        UserRepository userRepository
    ) {
        super(pictureRepository, pictureFormatRepository, statusRepository);
        this.tournamentRepository = tournamentRepository;
        this.squadRepository = squadRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected List<TournamentEntity> getTournament(Long request) {
        return tournamentRepository.findById(request).map(List::of)
            .orElseThrow(() -> RecordInDbNotFoundException.of(TournamentEntity.Names.TABLE_NAME));
    }

    @Override
    protected TournamentInstance mapToInstance(TournamentEntity tournament) {
        return TournamentMapper.INSTANCE.mapToInstance(tournament);
    }

    @Override
    protected void enrichAdditionalFields(TournamentEntity tournament, TournamentInstance instance) {
        instance.setSquadInstance(createSquadInstance(tournament.getSquadId()));
        instance.setOpponentsSquadInstance(createSquadInstance(tournament.getOpponentsSquadId()));
    }

    @Override
    protected GetTournamentDetailResponse mapToResponse(List<TournamentInstance> tournament) {
        return tournament.stream().findFirst()
            .map(TournamentMapper.INSTANCE::mapDetails)
            .orElse(null);
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
