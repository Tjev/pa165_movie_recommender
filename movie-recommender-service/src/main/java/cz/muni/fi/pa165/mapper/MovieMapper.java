package cz.muni.fi.pa165.mapper;

import cz.muni.fi.pa165.dto.movie.MovieCreateDTO;
import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.movie.MovieDetailedDTO;
import cz.muni.fi.pa165.entity.Movie;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.Base64;
import java.util.List;

/**
 * Mapper for {@link Movie} entity and its DTOs.
 *
 * @author Radoslav Chudovsky
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE, componentModel = "spring")
public interface MovieMapper {

    default byte[] toBytes(String string) {
        if (string == null) {
            return null;
        }
        return Base64.getDecoder().decode(string);
    }

    default String toStr(byte[] bytes) {
        return (bytes != null) ? Base64.getEncoder().encodeToString(bytes) : null;
    }

    MovieDTO movieToMovieDTO(Movie movie);
    Movie movieDTOToMovie(MovieDTO movieDTO);

    MovieDetailedDTO movieToMovieDetailedDTO(Movie movie);
    Movie movieDetailedDTOToMovie(MovieDetailedDTO movieDetailedDTO);

    MovieCreateDTO movieToMovieCreateDTO(Movie movie);
    Movie movieCreateDTOToMovie (MovieCreateDTO movieCreateDTO);

    List<MovieDetailedDTO> movieListToMovieDetailedDTOList(List<Movie> movies);
    List<MovieDTO> movieListToMovieDTOList(List<Movie> movies);
}
