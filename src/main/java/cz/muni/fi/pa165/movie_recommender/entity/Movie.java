package cz.muni.fi.pa165.movie_recommender.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class that represents a movie
 *
 * @author Radoslav Chudovsky
 */
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String bio;

    private LocalDate year;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<Genre> genres = new HashSet<>();

    @Lob
    private byte[] graphics;

    @ManyToMany()
    private Set<Person> directors = new HashSet<>();

    @ManyToMany()
    private Set<Person> actors = new HashSet<>();

    public Movie() {
    }

    public Movie(String title, String bio, LocalDate year, Set<Genre> genres,
                 byte[] graphics) {
        this.title = title;
        this.bio = bio;
        this.year = year;
        this.genres = genres;
        this.graphics = graphics;
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

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public byte[] getGraphics() {
        return graphics;
    }

    public void setGraphics(byte[] graphics) {
        this.graphics = graphics;
    }

    public Set<Person> getDirectors() {
        return Collections.unmodifiableSet(directors);
    }

    /**
     * Adds many-to-many connection to the given director
     * and this movie.
     *
     * @param director director that directed this movie
     */
    public void addDirector(Person director) {
        directors.add(director);
    }

    public Set<Person> getActors() {
        return Collections.unmodifiableSet(actors);
    }

    /**
     * Adds many-to-many connection to the given actor
     * and this movie.
     *
     * @return actor that acted in this movie
     */
    public void addActor(Person actor) {
        actors.add(actor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (!getTitle().equals(movie.getTitle())) return false;
        if (getBio() != null ? !getBio().equals(movie.getBio()) : movie.getBio() != null) return false;
        if (getYear() != null ? !getYear().equals(movie.getYear()) : movie.getYear() != null) return false;
        if (!getGenres().equals(movie.getGenres())) return false;
        if (getDirectors() != null ? !getDirectors().equals(movie.getDirectors()) : movie.getDirectors() != null)
            return false;
        return getActors() != null ? getActors().equals(movie.getActors()) : movie.getActors() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getTitle(),
                getBio(), getYear(),
                getGenres(),
                getDirectors(),
                getActors());
    }
}
