package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.facade.RatingFacade;
import cz.muni.fi.pa165.MovieService;
import cz.muni.fi.pa165.RatingService;
import cz.muni.fi.pa165.ScoreComputationService;
import cz.muni.fi.pa165.UserService;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
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
    public Optional<RatingDTO> create(RatingCreateDTO ratingCreateDTO) {
        try {
            Rating rating = ratingMapper.ratingCreateDTOToRating(ratingCreateDTO);
            Rating createdRating = ratingService.create(rating);
            return Optional.of(ratingMapper.ratingToRatingDTO(createdRating));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RatingDTO> update(RatingDTO ratingDTO) {
        try {
            Rating rating = ratingMapper.ratingDTOToRating(ratingDTO);
            Rating updatedRating = ratingService.update(rating);
            return Optional.of(ratingMapper.ratingToRatingDTO(updatedRating));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean remove(RatingDTO ratingDTO) {
        try {
            Rating rating = ratingMapper.ratingDTOToRating(ratingDTO);
            ratingService.remove(rating);
            return true;
        } catch (ServiceLayerException e) {
            return false;
        }
    }

    @Override
    public Optional<RatingDTO> findById(Long id) {
        try {
            Rating rating = ratingService.findById(id);
            return Optional.of(ratingMapper.ratingToRatingDTO(rating));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<RatingDTO>> findByMovie(MovieDTO movieDTO) {
        try {
            Movie movie = movieService.findById(movieDTO.getId());
            List<Rating> ratings = ratingService.findByMovie(movie);
            return Optional.of(ratingMapper.mapRatingsToRatingDTOs(ratings));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<RatingDTO>> findByUser(UserDTO userDTO) {
        try {
            User user = userService.findById(userDTO.getId());
            List<Rating> ratings = ratingService.findByUser(user);
            return Optional.of(ratingMapper.mapRatingsToRatingDTOs(ratings));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> getOverallScore(RatingDTO ratingDTO) {
        try {
            Rating rating = ratingMapper.ratingDTOToRating(ratingDTO);
            return Optional.of(scoreComputationService.getOverallScoreForRating(rating));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }
}
