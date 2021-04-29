package cz.fi.muni.pa165.dto;

import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for Movie entity.
 *
 * @author Radoslav Chudovsky
 */
public class MovieDTO {

    private Long id;
    private String title;
    private String bio;
    private LocalDate releaseYear;
    private Set<Genre> genres;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (!getTitle().equals(movie.getTitle())) return false;
        if (getBio() != null ? !getBio().equals(movie.getBio()) : movie.getBio() != null) return false;
        if (getReleaseYear() != null ? !getReleaseYear().equals(movie.getYear()) : movie.getYear() != null) return false;
        return getGenres().equals(movie.getGenres());
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
        return "MovieDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", bio='" + bio + '\'' +
                ", releaseYear=" + releaseYear +
                ", genres=" + genres +
                '}';
    }
}
