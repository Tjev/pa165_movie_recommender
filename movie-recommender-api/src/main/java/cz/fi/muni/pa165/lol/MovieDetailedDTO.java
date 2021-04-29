package cz.fi.muni.pa165.dto;

import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for Movie entity with relationships to other DTOs.
 *
 * @author Radoslav Chudovsky
 */
public class MovieDetailedDTO {

    private Long id;
    private String title;
    private String bio;
    private LocalDate releaseYear;
    private Set<Genre> genres;

    private Set<PersonDTO> directors;
    private Set<PersonDTO> actors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(LocalDate releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<PersonDTO> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<PersonDTO> directors) {
        this.directors = directors;
    }

    public Set<PersonDTO> getActors() {
        return actors;
    }

    public void setActors(Set<PersonDTO> actors) {
        this.actors = actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDetailedDTO)) return false;

        MovieDetailedDTO that = (MovieDetailedDTO) o;

        if (!getTitle().equals(that.getTitle())) return false;
        if (getBio() != null ? !getBio().equals(that.getBio()) : that.getBio() != null) return false;
        if (!getReleaseYear().equals(that.getReleaseYear())) return false;
        return getGenres().equals(that.getGenres());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getTitle(),
                getBio(),
                getReleaseYear(),
                getGenres());
    }

    @Override
    public String toString() {
        return "MovieDetailedDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", bio='" + bio + '\'' +
                ", releaseYear=" + releaseYear +
                ", genres=" + genres +
                ", directors=" + directors +
                ", actors=" + actors +
                '}';
    }
}
