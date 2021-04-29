package cz.fi.muni.pa165.dto;

import cz.muni.fi.pa165.entity.Genre;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

/**
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
        if (o == null || getClass() != o.getClass()) return false;

        MovieCreateDTO that = (MovieCreateDTO) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (bio != null ? !bio.equals(that.bio) : that.bio != null) return false;
        if (releaseYear != null ? !releaseYear.equals(that.releaseYear) : that.releaseYear != null) return false;
        if (genres != null ? !genres.equals(that.genres) : that.genres != null) return false;
        return Arrays.equals(graphics, that.graphics);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (releaseYear != null ? releaseYear.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(graphics);
        return result;
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
