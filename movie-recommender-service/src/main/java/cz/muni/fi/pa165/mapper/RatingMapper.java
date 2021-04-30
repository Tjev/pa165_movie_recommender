package cz.muni.fi.pa165.mapper;

import cz.fi.muni.pa165.dto.RatingDTO;
import cz.muni.fi.pa165.entity.Rating;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

/**
 * Mapper for Rating entity.
 *
 * @author Kristian Tkacik
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE, componentModel = "spring")
public interface RatingMapper {

    RatingDTO map(Rating rating);
    Rating map(RatingDTO ratingDTO);
}
