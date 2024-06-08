package com.lelar.processor.get;

import com.lelar.database.dao.ClassicTournamentRepository;
import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.entity.ClassicPictureBindingEntity;
import com.lelar.database.entity.ClassicTournamentEntity;
import com.lelar.dto.tournament.classic.get.GetClassicTournamentResponse;
import com.lelar.dto.tournament.GetTournamentRequest;
import com.lelar.instance.TournamentInstance;
import com.lelar.mapper.TournamentMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObtainClassicTournamentProcessor extends ObtainCommonTournamentProcessor<
    ClassicTournamentEntity,
    ClassicPictureBindingEntity,
    GetTournamentRequest,
    GetClassicTournamentResponse
    > {
    private final ClassicTournamentRepository tournamentRepository;

    public ObtainClassicTournamentProcessor(
        PictureRepository pictureRepository,
        PictureFormatRepository pictureFormatRepository,
        TournamentStatusRepository statusRepository,
        ClassicTournamentRepository tournamentRepository
    ) {
        super(pictureRepository, pictureFormatRepository, statusRepository);
        this.tournamentRepository = tournamentRepository;
    }


    @Override
    protected List<ClassicTournamentEntity> getTournament(GetTournamentRequest request) {
        return tournamentRepository.findByDateAndGender(
            request.getStartBeginDate(),
            request.getStartEndDate(),
            request.getGender().getGenderKey()
        );
    }

    @Override
    protected TournamentInstance mapToInstance(ClassicTournamentEntity tournament) {
        return TournamentMapper.INSTANCE.mapToInstance(tournament);
    }

    @Override
    protected void enrichAdditionalFields(ClassicTournamentEntity tournament, TournamentInstance instance) {

    }

    @Override
    protected GetClassicTournamentResponse mapToResponse(List<TournamentInstance> instances) {
        return new GetClassicTournamentResponse().setTournaments(
            instances.stream()
                .map(TournamentMapper.INSTANCE::mapClassic)
                .collect(Collectors.toSet())
        );
    }

}
