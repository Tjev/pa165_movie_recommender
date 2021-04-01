package cz.muni.fi.pa165.movie_recommender.entity;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

//TODO: Decide what entity (Person/Movie) should own many-to-many relationships.

/**
 * Represents each person involved in movie industry.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private LocalDate dateOfBirth;

    private String bio;

    @ManyToMany
    private Set<Movie> directedMovies;

    @ManyToMany
    private Set<Movie> actsInMovies;

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public Set<Movie> getDirectedMovies() {
        return Collections.unmodifiableSet(directedMovies);
    }

    public Set<Movie> getActsInMovies() {
        return Collections.unmodifiableSet(actsInMovies);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Adds connection to movie that has been directed by the person.
     *
     * @param movie that has been directed by this person
     */
    public void addDirectedMovie(Movie movie) {
        directedMovies.add(movie);
    }

    /**
     * Adds connection to movie the person acted in.
     *
     * @param movie in which the person acted
     */
    public void addActsInMovie(Movie movie) {
        actsInMovies.add(movie)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getName().equals(person.getName())
                && Objects.equals(getDateOfBirth(), person.getDateOfBirth())
                && Objects.equals(getBio(), person.getBio())
                && Objects.equals(getDirectedMovies(), person.getDirectedMovies())
                && Objects.equals(getActsInMovies(), person.getActsInMovies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDateOfBirth(), getBio(), getDirectedMovies(), getActsInMovies());
    }

}
