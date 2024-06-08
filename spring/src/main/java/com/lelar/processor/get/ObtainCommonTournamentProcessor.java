package com.lelar.processor.get;

import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentStatusRepository;
import com.lelar.database.entity.CommonPictureBindingEntity;
import com.lelar.database.entity.CommonTournamentEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.TournamentStatusEntity;
import com.lelar.exception.ApplicationException;
import com.lelar.exception.RecordInDbNotFoundException;
import com.lelar.instance.PictureInstance;
import com.lelar.instance.TournamentInstance;
import com.lelar.processor.get.api.ObtainDataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public abstract class ObtainCommonTournamentProcessor<
    E extends CommonTournamentEntity<T>,
    T extends CommonPictureBindingEntity<E>,
    Q,
    S
    > implements ObtainDataProcessor<Q, S> {
    private final PictureRepository pictureRepository;
    private final PictureFormatRepository pictureFormatRepository;
    private final TournamentStatusRepository statusRepository;

    @Override
    public S process(Q request) throws ApplicationException {
        List<E> tournaments = getTournament(request);

        List<TournamentInstance> instances = new ArrayList<>();

        tournaments.forEach(
            tournament -> {
                Set<Long> pictureIds = tournament.getTournamentPictureRefs().stream()
                    .map(it -> it.getTournamentId().getId())
                    .collect(Collectors.toSet());

                List<PictureEntity> pictures = pictureRepository.findAllById(pictureIds);

                Set<Long> formatIds = pictures.stream()
                    .map(PictureEntity::getFormatId)
                    .map(AggregateReference::getId)
                    .collect(Collectors.toSet());
                List<PictureFormatEntity> formats = pictureFormatRepository.findAllById(formatIds);

                TournamentInstance tournamentInstance = createTournamentInstance(
                    tournament, pictures, formats
                );
                enrichAdditionalFields(tournament, tournamentInstance);
                instances.add(tournamentInstance);
            }
        );


        return mapToResponse(instances);
    }

    protected abstract List<E> getTournament(Q request);

    protected abstract TournamentInstance mapToInstance(E tournament);

    protected abstract void enrichAdditionalFields(E tournament, TournamentInstance instance);

    protected abstract S mapToResponse(List<TournamentInstance> tournament);

    private TournamentInstance createTournamentInstance(
        E tournament,
        List<PictureEntity> pictures,
        List<PictureFormatEntity> formats
    ) {
        Map<Long, PictureEntity> pictureWithIds = pictures.stream()
            .collect(Collectors.toMap(PictureEntity::getId, Function.identity()));
        Map<Long, PictureFormatEntity> formatWithIds = formats.stream()
            .collect(Collectors.toMap(PictureFormatEntity::getId, Function.identity()));

        TournamentInstance tournamentInstance = mapToInstance(tournament);

        tournamentInstance.getPictures().forEach(
            picture -> enrichPicture(pictureWithIds, formatWithIds, picture)
        );
        TournamentStatusEntity statusEntity = statusRepository.findById(tournament.getStatusId().getId())
            .orElseThrow(() -> RecordInDbNotFoundException.of(TournamentStatusEntity.Names.TABLE_NAME));

        tournamentInstance.setStatus(statusEntity.getStatusKey());

        return tournamentInstance;
    }

    private void enrichPicture(
        Map<Long, PictureEntity> pictureWithIds,
        Map<Long, PictureFormatEntity> formatWithIds,
        PictureInstance picture
    ) {
        PictureEntity entity = pictureWithIds.get(picture.getId());

        picture.setId(entity.getId());
        picture.setLocalPath(entity.getLocalPath());
        picture.setServerPath(entity.getServerPath());
        picture.setFormat(formatWithIds.get(picture.getId()).getFormat());
    }

}
