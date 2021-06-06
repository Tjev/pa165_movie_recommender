package cz.muni.fi.pa165.dto.movie;

import cz.muni.fi.pa165.dto.person.PersonDTO;
import cz.muni.fi.pa165.dto.rating.RatingDTO;
import cz.muni.fi.pa165.entity.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

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
    private ArrayList<Genre> genres;
    private String graphics;

    private ArrayList<PersonDTO> directors;
    private ArrayList<PersonDTO> actors;
    private ArrayList<RatingDTO> ratings;

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

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<PersonDTO> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<PersonDTO> directors) {
        this.directors = directors;
    }

    public ArrayList<PersonDTO> getActors() {
        return actors;
    }

    public void setActors(ArrayList<PersonDTO> actors) {
        this.actors = actors;
    }

    public ArrayList<RatingDTO> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<RatingDTO> ratings) {
        this.ratings = ratings;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
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
