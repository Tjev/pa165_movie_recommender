package cz.muni.fi.pa165.mapper;

import cz.fi.muni.pa165.dto.RatingCreateDTO;
import cz.fi.muni.pa165.dto.RatingDTO;
import cz.muni.fi.pa165.entity.Rating;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for Rating entity.
 *
 * @author Kristian Tkacik
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE, componentModel = "spring")
public interface RatingMapper {

    RatingDTO ratingToRatingDTO(Rating rating);
    Rating ratingDTOToRating(RatingDTO ratingDTO);

    RatingCreateDTO ratingToRatingCreateDTO(Rating rating);
    Rating ratingCreateDTOToRating(RatingCreateDTO ratingCreateDTO);

    List<RatingDTO> mapRatingsToRatingDTOs(List<Rating> ratings);
}
