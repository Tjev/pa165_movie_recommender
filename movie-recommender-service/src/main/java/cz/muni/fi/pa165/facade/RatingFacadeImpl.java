package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.MovieDTO;
import cz.fi.muni.pa165.dto.RatingCreateDTO;
import cz.fi.muni.pa165.dto.RatingDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.facade.RatingFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RatingFacadeImpl implements RatingFacade {

    @Override
    public Long create(RatingCreateDTO rating) {
        return null;
    }

    @Override
    public RatingDTO update(RatingDTO rating) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public RatingDTO findById(Long id) {
        return null;
    }

    @Override
    public List<RatingDTO> findByMovie(MovieDTO movie) {
        return null;
    }

    @Override
    public List<RatingDTO> findByUser(UserDTO user) {
        return null;
    }
}
