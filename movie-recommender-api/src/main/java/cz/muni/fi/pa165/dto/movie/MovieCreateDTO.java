package cz.muni.fi.pa165.dto.movie;

import cz.muni.fi.pa165.entity.Genre;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * DTO for Movie entity (for creation purposes).
 *
 * @author Radoslav Chudovsky
 */
public class MovieCreateDTO {

    @NotNull
    @Size(min = 1, max = 255, message = "Title must be long at least 1 at most 255 characters.")
    private String title;

    @Size(max = 511, message = "Bio can be long at least 1 at most 511 characters.")
    private String bio;

    @NotNull
    private LocalDate releaseYear;

    @NotEmpty(message = "Genres cannot be empty.")
    private ArrayList<Genre> genres;

    private String graphics;

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

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
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
                '}';
    }
}
