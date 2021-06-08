package cz.muni.fi.pa165.dto.movie;

import cz.muni.fi.pa165.entity.Genre;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * DTO for Movie entity.
 *
 * @author Radoslav Chudovsky
 */
public class MovieDTO {

    @NotNull
    private Long id;
    private String title;
    private String bio;
    private LocalDate releaseYear;
    private ArrayList<Genre> genres;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDTO)) return false;

        MovieDTO movieDTO = (MovieDTO) o;

        if (!getTitle().equals(movieDTO.getTitle())) return false;
        if (getBio() != null ? !getBio().equals(movieDTO.getBio()) : movieDTO.getBio() != null) return false;
        if (!getReleaseYear().equals(movieDTO.getReleaseYear())) return false;
        return getGenres().equals(movieDTO.getGenres());
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
