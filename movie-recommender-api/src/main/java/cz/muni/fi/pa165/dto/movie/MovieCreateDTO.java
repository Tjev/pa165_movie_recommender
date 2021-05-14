package cz.muni.fi.pa165.dto.movie;

import cz.muni.fi.pa165.entity.Genre;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for Movie entity (for creation purposes).
 *
 * @author Radoslav Chudovsky
 */
public class MovieCreateDTO {

    private String title;
    private String bio;
    private LocalDate releaseYear;
    private Set<Genre> genres;
    private byte[] graphics;

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

    public byte[] getGraphics() {
        return graphics;
    }

    public void setGraphics(byte[] graphics) {
        this.graphics = graphics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieCreateDTO)) return false;

        MovieCreateDTO that = (MovieCreateDTO) o;

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
        return "MovieCreateDTO{" +
                "title='" + title + '\'' +
                ", bio='" + bio + '\'' +
                ", releaseYear=" + releaseYear +
                ", genres=" + genres +
                ", graphics=" + Arrays.toString(graphics) +
                '}';
    }
}
