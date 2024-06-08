package com.lelar.service.update;

import com.lelar.database.dao.ClassicTournamentRepository;
import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.entity.ClassicPictureBindingEntity;
import com.lelar.database.entity.ClassicTournamentEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.TournamentStatusEntity;
import com.lelar.dto.picture.Picture;
import com.lelar.dto.tournament.classic.update.UpdateClassicTournamentRequest;
import com.lelar.exception.ApplicationException;
import com.lelar.mapper.PictureMapper;
import com.lelar.mapper.TournamentMapper;
import com.lelar.service.update.api.UpdateDataService;
import lombok.Data;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
public class UpdateClassicTournamentDataService implements UpdateDataService<UpdateClassicTournamentRequest> {

    private final ClassicTournamentRepository tournamentRepository;
    private final PictureRepository pictureRepository;
    private final PictureFormatRepository pictureFormatRepository;
    private final TournamentStatusRepository statusRepository;

    //TODO Отрефачить
    @Override
    public Long update(UpdateClassicTournamentRequest request) throws ApplicationException {

        Set<Picture> pictures = request.getPictures();

        Set<String> pictureFormats = pictures.stream().map(Picture::getFormat).collect(Collectors.toSet());
        List<PictureFormatEntity> allBy = pictureFormatRepository.findAllBy(pictureFormats);

        Map<String, Long> map = allBy.stream().collect(
            Collectors.toMap(PictureFormatEntity::getFormat, PictureFormatEntity::getId)
        );

        Set<PictureEntity> pictureEntities = pictures.stream()
            .map(it -> PictureMapper.INSTANCE.map(it, map))
            .collect(Collectors.toSet());

        List<PictureEntity> savedPictureEntities = pictureRepository.saveAll(pictureEntities);

        ClassicTournamentEntity entity = TournamentMapper.INSTANCE.mapClassic(request);

        Set<ClassicPictureBindingEntity> collect = savedPictureEntities.stream().map(
            it -> {
                ClassicPictureBindingEntity pictureBindingEntity = new ClassicPictureBindingEntity();
                pictureBindingEntity.setPictureId(AggregateReference.to(it.getId()));
                return pictureBindingEntity;
            }
        ).collect(Collectors.toSet());
        entity.setTournamentPictureRefs(collect);

        TournamentStatusEntity status = statusRepository.findByKey(request.getStatus().name());
        entity.setStatusId(AggregateReference.to(status.getId()));

        tournamentRepository.save(entity);

        return entity.getId();
    }

}
