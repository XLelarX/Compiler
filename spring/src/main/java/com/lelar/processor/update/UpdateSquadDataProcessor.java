package com.lelar.processor.update;

import com.lelar.database.dao.PictureFormatRepository;
import com.lelar.database.dao.PictureRepository;
import com.lelar.database.dao.TournamentRepository;
import com.lelar.database.entity.SquadBindingEntity;
import com.lelar.database.entity.SquadEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.dto.squad.update.UpdateSquadRequest;
import com.lelar.exception.ApplicationException;
import com.lelar.processor.update.api.UpdateDataProcessor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class UpdateSquadDataProcessor implements UpdateDataProcessor<UpdateSquadRequest> {

    private final TournamentRepository tournamentRepository;
    private final PictureRepository pictureRepository;
    private final PictureFormatRepository pictureFormatRepository;
    private final ListCrudRepository<SquadEntity, Long> squadRepository;

    @Override
    public Long update(UpdateSquadRequest request) throws ApplicationException {
        SquadEntity squadEntity = new SquadEntity();
        squadEntity.setName(request.getName());
        squadEntity.setId(request.getSquadId());

        Set<SquadBindingEntity> squadRefs = request.getUsersIds().stream()
            .map(AggregateReference::<UserEntity, Long>to)
            .map(it -> new SquadBindingEntity().setUserId(it))
            .collect(Collectors.toSet());
        squadEntity.setSquadUserRefs(squadRefs);

        return squadRepository.save(squadEntity).getId();
    }

}
