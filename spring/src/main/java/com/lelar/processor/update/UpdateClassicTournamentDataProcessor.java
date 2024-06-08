package com.lelar.processor.update;

import com.lelar.database.dao.ClassicTournamentRepository;
import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.entity.ClassicPictureBindingEntity;
import com.lelar.database.entity.ClassicTournamentEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.dto.tournament.classic.update.UpdateClassicTournamentRequest;
import com.lelar.mapper.TournamentMapper;
import com.lelar.processor.update.api.BaseUpdateTournamentDataProcessor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

@Service
public class UpdateClassicTournamentDataProcessor extends BaseUpdateTournamentDataProcessor<UpdateClassicTournamentRequest, ClassicTournamentEntity, ClassicPictureBindingEntity> {

    private final ClassicTournamentRepository tournamentRepository;

    public UpdateClassicTournamentDataProcessor(
        PictureRepository pictureRepository,
        PictureFormatRepository pictureFormatRepository,
        TournamentStatusRepository statusRepository,
        ClassicTournamentRepository tournamentRepository
    ) {
        super(pictureRepository, pictureFormatRepository, statusRepository);
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    protected void saveEntity(ClassicTournamentEntity entity) {
        tournamentRepository.save(entity);
    }

    @Override
    protected ClassicPictureBindingEntity mapPicture(PictureEntity pictureEntity) {
        ClassicPictureBindingEntity pictureBindingEntity = new ClassicPictureBindingEntity();
        pictureBindingEntity.setPictureId(AggregateReference.to(pictureEntity.getId()));
        return pictureBindingEntity;
    }

    @Override
    protected ClassicTournamentEntity mapRequestToEntity(UpdateClassicTournamentRequest request) {
        return TournamentMapper.INSTANCE.mapClassic(request);
    }

}
