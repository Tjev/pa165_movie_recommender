package cz.muni.fi.pa165.movie_recommender.entity;

import javax.persistence.*;
import java.time.LocalDate;
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
        return directors;
    }

    public void addDirector(Person director) {
        directors.add(director);
    }

    public Set<Person> getActors() {
        return actors;
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
        return getGenres().equals(movie.getGenres());
    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + (getBio() != null ? getBio().hashCode() : 0);
        result = 31 * result + (getYear() != null ? getYear().hashCode() : 0);
        result = 31 * result + getGenres().hashCode();
        return result;
    }
}
