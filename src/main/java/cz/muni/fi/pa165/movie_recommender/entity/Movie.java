package cz.muni.fi.pa165.movie_recommender.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    private Set<Person> directors = new HashSet<>();

    @ManyToMany
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

    public byte[] getGraphics() {
        return graphics;
    }

    public void setGraphics(byte[] graphics) {
        this.graphics = graphics;
    }

    public Set<Person> getDirectors() {
        return Collections.unmodifiableSet(directors);
    }

    public void addDirector(Person director) {
        directors.add(director);
    }

    public Set<Person> getActors() {
        return Collections.unmodifiableSet(actors);
    }

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
        int result = getTitle().hashCode();
        result = 31 * result + (getBio() != null ? getBio().hashCode() : 0);
        result = 31 * result + (getYear() != null ? getYear().hashCode() : 0);
        result = 31 * result + getGenres().hashCode();
        result = 31 * result + (getDirectors() != null ? getDirectors().hashCode() : 0);
        result = 31 * result + (getActors() != null ? getActors().hashCode() : 0);
        return result;
    }
}
