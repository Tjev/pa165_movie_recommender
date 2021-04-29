package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.facade.RatingFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Rating facade implementation.
 *
 * @author Kristian Tkacik
 */
@Service
@Transactional
public class RatingFacadeImpl implements RatingFacade {

    @Override
    public RatingDTO create(RatingCreateDTO rating) {
        return null;
    }

    @Override
    public RatingDTO update(RatingDTO rating) {
        return null;
    }

    @Override
    public void remove(Long id) {

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
