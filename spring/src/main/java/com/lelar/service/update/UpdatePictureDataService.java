package com.lelar.service.update;

import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.database.entity.PictureBindingEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.picture.update.UpdatePictureRequest;
import com.lelar.exception.ApplicationException;
import com.lelar.exception.StorePictureException;
import com.lelar.storage.picture.api.StorePictureService;
import com.lelar.service.update.api.UpdateDataService;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
@Data
public class UpdatePictureDataService implements UpdateDataService<UpdatePictureRequest> {

    private final TournamentRepository tournamentRepository;
    private final PictureRepository pictureRepository;
    private final PictureFormatRepository pictureFormatRepository;
    private final StorePictureService storePictureService;

    @Override
    public void update(UpdatePictureRequest request) throws ApplicationException {
        Pair<String, String> storedPictureData;
        try {
            storedPictureData = storePictureService.storePicture(request.getFile());
        } catch (IOException e) {
            throw new StorePictureException();
        }

        Optional<PictureFormatEntity> format = pictureFormatRepository.findAllBy(Set.of(storedPictureData.getLeft()))
            .stream()
            .findFirst();

        if (format.isEmpty()) {
            format = Optional.ofNullable(pictureFormatRepository.save(createPictureFormatEntity(storedPictureData)));
        }

        PictureEntity savedPicture = pictureRepository.save(createPictureEntity(request, storedPictureData, format.get()));
        Optional<TournamentEntity> tournament = tournamentRepository.findById(request.getTournamentId());
        tournament.ifPresent(
            it -> {
                Set<PictureBindingEntity> tournamentPictureRefs = it.getTournamentPictureRefs();
                PictureBindingEntity pictureBindingEntity = new PictureBindingEntity();
                pictureBindingEntity.setTournamentId(AggregateReference.to(it.getId()));
                pictureBindingEntity.setPictureId(AggregateReference.to(savedPicture.getId()));
                tournamentPictureRefs.add(pictureBindingEntity);
                tournamentRepository.save(it);
            }
        );

    }

    private PictureFormatEntity createPictureFormatEntity(Pair<String, String> storedPictureData) {
        PictureFormatEntity pictureFormatEntity = new PictureFormatEntity();
        pictureFormatEntity.setFormat(storedPictureData.getLeft());
        return pictureFormatEntity;
    }

    private PictureEntity createPictureEntity(
        UpdatePictureRequest request,
        Pair<String, String> serverPath,
        PictureFormatEntity format
    ) {
        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setServerPath(serverPath.getRight());
        pictureEntity.setLocalPath(request.getLocalPath());
        pictureEntity.setFormatId(AggregateReference.to(format.getId()));
        return pictureEntity;
    }

}
