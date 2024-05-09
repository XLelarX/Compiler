package com.lelar.service.update;

import com.lelar.database.dao.TournamentRepository;
import com.lelar.dto.tournament.update.UpdateTournamentRequest;
import com.lelar.exception.ApplicationException;
import com.lelar.service.update.api.UpdateDataService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class UpdateDataTournamentResponse implements UpdateDataService<UpdateTournamentRequest> {

    private final TournamentRepository tournamentRepository;

    @Override
    public boolean update(UpdateTournamentRequest request) throws ApplicationException {
        tournamentRepository.save(request.getEntity());
        return true;
    }

}
