package cz.fi.muni.pa165.dto;

import cz.muni.fi.pa165.entity.Genre;

import java.time.LocalDate;
import java.util.Set;

/**
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
        if (o == null || getClass() != o.getClass()) return false;

        MovieDTO movieDTO = (MovieDTO) o;

        if (title != null ? !title.equals(movieDTO.title) : movieDTO.title != null) return false;
        if (bio != null ? !bio.equals(movieDTO.bio) : movieDTO.bio != null) return false;
        if (releaseYear != null ? !releaseYear.equals(movieDTO.releaseYear) : movieDTO.releaseYear != null)
            return false;
        return genres != null ? genres.equals(movieDTO.genres) : movieDTO.genres == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (releaseYear != null ? releaseYear.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        return result;
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
