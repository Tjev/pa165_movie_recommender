package cz.muni.fi.pa165.mapper;

import cz.muni.fi.pa165.dto.MovieCreateDTO;
import cz.muni.fi.pa165.dto.MovieDTO;
import cz.muni.fi.pa165.dto.MovieDetailedDTO;
import cz.muni.fi.pa165.entity.Movie;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for {@link Movie} entity and its DTOs.
 *
 * @author Radoslav Chudovsky
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE, componentModel = "spring")
public interface MovieMapper {

    MovieDTO movieToMovieDTO(Movie movie);
    Movie movieDTOToMovie(MovieDTO movieDTO);

    MovieDetailedDTO movieToMovieDetailedDTO(Movie movie);
    Movie movieDetailedDTOToMovie(MovieDetailedDTO movieDetailedDTO);

    MovieCreateDTO movieToMovieCreateDTO(Movie movie);
    Movie movieCreateDTOToMovie (MovieCreateDTO movieCreateDTO);

    List<MovieDetailedDTO> movieListToMovieDetailedDTOList(List<Movie> movies);
    List<MovieDTO> movieListToMovieDTOList(List<Movie> movies);
}
