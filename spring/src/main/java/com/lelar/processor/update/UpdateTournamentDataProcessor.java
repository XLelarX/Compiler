package com.lelar.processor.update;

import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.entity.PictureBindingEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.tournament.beach.update.UpdateBeachTournamentRequest;
import com.lelar.mapper.TournamentMapper;
import com.lelar.processor.update.api.BaseUpdateTournamentDataProcessor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

@Service
public class UpdateTournamentDataProcessor extends BaseUpdateTournamentDataProcessor<UpdateBeachTournamentRequest, TournamentEntity, PictureBindingEntity> {

    private final TournamentRepository tournamentRepository;

    public UpdateTournamentDataProcessor(
        PictureRepository pictureRepository,
        PictureFormatRepository pictureFormatRepository,
        TournamentStatusRepository statusRepository,
        TournamentRepository tournamentRepository
    ) {
        super(pictureRepository, pictureFormatRepository, statusRepository);
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    protected PictureBindingEntity mapPicture(PictureEntity pictureEntity) {
        PictureBindingEntity pictureBindingEntity = new PictureBindingEntity();
        pictureBindingEntity.setPictureId(AggregateReference.to(pictureEntity.getId()));
        return pictureBindingEntity;
    }

    @Override
    protected TournamentEntity mapRequestToEntity(UpdateBeachTournamentRequest request) {
        return TournamentMapper.INSTANCE.map(request);
    }

    @Override
    protected void saveEntity(TournamentEntity entity) {
        tournamentRepository.save(entity);
    }

}
