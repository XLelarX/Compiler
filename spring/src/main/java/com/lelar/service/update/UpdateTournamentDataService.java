package com.lelar.service.update;

import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.database.entity.PictureBindingEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.picture.Picture;
import com.lelar.dto.tournament.update.UpdateTournamentRequest;
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
public class UpdateTournamentDataService implements UpdateDataService<UpdateTournamentRequest> {

    private final TournamentRepository tournamentRepository;
    private final PictureRepository pictureRepository;
    private final PictureFormatRepository pictureFormatRepository;

    //TODO Отрефачить
    @Override
    public void update(UpdateTournamentRequest request) throws ApplicationException {
        //TODO Добавить проверку на формат картинки

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

        TournamentEntity entity = TournamentMapper.INSTANCE.map(request);

        Set<PictureBindingEntity> collect = savedPictureEntities.stream().map(
            it -> {
                PictureBindingEntity pictureBindingEntity = new PictureBindingEntity();
                pictureBindingEntity.setPictureId(AggregateReference.to(it.getId()));
                return pictureBindingEntity;
            }
        ).collect(Collectors.toSet());
        entity.setTournamentPictureRefs(collect);
        tournamentRepository.save(entity);
    }

}
