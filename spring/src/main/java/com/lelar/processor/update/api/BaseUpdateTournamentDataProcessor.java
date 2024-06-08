package com.lelar.processor.update.api;

import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.entity.CommonPictureBindingEntity;
import com.lelar.database.entity.CommonTournamentEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.TournamentStatusEntity;
import com.lelar.dto.picture.Picture;
import com.lelar.dto.tournament.UpdateTournamentBase;
import com.lelar.exception.ApplicationException;
import com.lelar.mapper.PictureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class BaseUpdateTournamentDataProcessor<
    T extends UpdateTournamentBase,
    E extends CommonTournamentEntity<P>,
    P extends CommonPictureBindingEntity<E>
    > implements UpdateDataProcessor<T> {

    private final PictureRepository pictureRepository;
    private final PictureFormatRepository pictureFormatRepository;
    private final TournamentStatusRepository statusRepository;

    @Override
    public Long update(T request) throws ApplicationException {
        E entity = mapRequestToEntity(request);

        enrichPictures(request, entity);
        enrichSquad(request, entity);

        saveEntity(entity);

        return entity.getId();
    }

    private void enrichSquad(T request, E entity) {
        TournamentStatusEntity status = statusRepository.findByKey(request.getStatus().name());
        entity.setStatusId(AggregateReference.to(status.getId()));
    }

    private void enrichPictures(T request, E entity) {
        Set<Picture> pictures = request.getPictures();

        Set<String> pictureFormats = pictures.stream().map(Picture::getFormat).collect(Collectors.toSet());

        Map<String, Long> pictureFormatsFromDb = pictureFormatRepository.findAllBy(pictureFormats).stream()
            .collect(Collectors.toMap(PictureFormatEntity::getFormat, PictureFormatEntity::getId));

        Set<PictureEntity> pictureEntities = pictures.stream()
            .map(it -> PictureMapper.INSTANCE.map(it, pictureFormatsFromDb))
            .collect(Collectors.toSet());

        List<PictureEntity> savedPictureEntities = pictureRepository.saveAll(pictureEntities);

        entity.setTournamentPictureRefs(
            savedPictureEntities.stream()
                .map(this::mapPicture)
                .collect(Collectors.toSet())
        );
    }

    protected abstract P mapPicture(PictureEntity pictureEntity);

    protected abstract E mapRequestToEntity(T request);

    protected abstract void saveEntity(E entity);

}
