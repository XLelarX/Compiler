package com.lelar.service.update.api;

import com.lelar.database.repository.api.TournamentRepository;
import com.lelar.dto.tournament.update.UpdateTournamentRequest;
import com.lelar.exception.ApplicationException;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class UpdateTournamentResponse implements UpdateService<UpdateTournamentRequest> {

    private final TournamentRepository tournamentRepository;

    @Override
    public boolean update(UpdateTournamentRequest request) throws ApplicationException {
        tournamentRepository.update(request.getEntity());
        return true;
    }

}
