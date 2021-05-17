package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.rating.RatingCreateDTO;
import cz.muni.fi.pa165.dto.rating.RatingDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.exception.FacadeLayerException;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.RatingService;
import cz.muni.fi.pa165.service.ScoreComputationService;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import cz.muni.fi.pa165.mapper.RatingMapper;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Rating facade implementation.
 *
 * @author Kristian Tkacik
 */
@Service
@Transactional
public class RatingFacadeImpl implements RatingFacade {

    private final RatingService ratingService;
    private final UserService userService;
    private final MovieService movieService;
    private final ScoreComputationService scoreComputationService;
    private final RatingMapper ratingMapper;

    @Inject
    public RatingFacadeImpl(RatingService ratingService, UserService userService, MovieService movieService,
                            ScoreComputationService scoreComputationService, RatingMapper ratingMapper) {
        this.ratingService = ratingService;
        this.userService = userService;
        this.movieService = movieService;
        this.scoreComputationService = scoreComputationService;
        this.ratingMapper = ratingMapper;
    }

    @Override
    public RatingDTO create(RatingCreateDTO ratingCreateDTO) {
        try {
            Rating rating = ratingMapper.ratingCreateDTOToRating(ratingCreateDTO);
            Rating createdRating = ratingService.create(rating);
            return ratingMapper.ratingToRatingDTO(createdRating);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public RatingDTO update(RatingDTO ratingDTO) {
        try {
            Rating rating = ratingMapper.ratingDTOToRating(ratingDTO);
            Rating updatedRating = ratingService.update(rating);
            return ratingMapper.ratingToRatingDTO(updatedRating);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public void remove(RatingDTO ratingDTO) {
        try {
            Rating rating = ratingMapper.ratingDTOToRating(ratingDTO);
            ratingService.remove(rating);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public Optional<RatingDTO> findById(Long id) {
        try {
            Rating rating = ratingService.findById(id);
            return Optional.of(ratingMapper.ratingToRatingDTO(rating));
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public List<RatingDTO> findByMovie(MovieDTO movieDTO) {
        try {
            Movie movie = movieService.findById(movieDTO.getId());
            List<Rating> ratings = ratingService.findByMovie(movie);
            return ratingMapper.mapRatingsToRatingDTOs(ratings);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public List<RatingDTO> findByUser(UserDTO userDTO) {
        try {
            User user = userService.findById(userDTO.getId());
            List<Rating> ratings = ratingService.findByUser(user);
            return ratingMapper.mapRatingsToRatingDTOs(ratings);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public BigDecimal getOverallScore(RatingDTO ratingDTO) {
        try {
            Rating rating = ratingMapper.ratingDTOToRating(ratingDTO);
            return scoreComputationService.getOverallScoreForRating(rating);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }
}
