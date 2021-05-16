package cz.muni.fi.pa165.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@Table(name = "movie")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "bio", length = 511)
    private String bio;

    @NotNull
    @Column(name = "release_year", nullable = false)
    private LocalDate releaseYear;

    @NotEmpty
    @Column(name = "genres", nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Genre> genres = new HashSet<>();

    @Lob
    @Column(name = "graphics")
    private byte[] graphics;

    @ManyToMany()
    @JoinTable(name = "director_movie")
    private Set<Person> directors = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "actor_movie")
    private Set<Person> actors = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    @Column(name = "ratings")
    private Set<Rating> ratings = new HashSet<>();

    public Movie() {
    }

    public Movie(String title, String bio, LocalDate year,
                 Set<Genre> genres, byte[] graphics) {
        this.title = title;
        this.bio = bio;
        this.releaseYear = year;
        this.genres = genres;
        this.graphics = graphics;
    }

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

    public void setReleaseYear(LocalDate year) {
        this.releaseYear = year;
    }

    public Set<Genre> getGenres() {
        return genres;
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
     * @param actor actor that acted in this movie
     */
    public void addActor(Person actor) {
        actors.add(actor);
    }

    public Set<Rating> getRatings() {
        return Collections.unmodifiableSet(ratings);
    }

    /**
     * Adds one-to-many connection to the given rating
     * and this movie.
     *
     * @param rating rating connected to this movie
     */
    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setMovie(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (!getTitle().equals(movie.getTitle())) return false;
        if (getBio() != null ? !getBio().equals(movie.getBio()) : movie.getBio() != null) return false;
        if (getReleaseYear() != null ? !getReleaseYear().equals(movie.getReleaseYear()) : movie.getReleaseYear() != null) return false;
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
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", bio='" + bio + '\'' +
                ", releaseYear=" + releaseYear +
                ", genres=" + genres +
                '}';
    }
}
