package cz.muni.fi.pa165.movie_recommender.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents each person involved in movie industry
 *
 * @author Jiri Papousek
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate dateOfBirth;

    private String bio;

    @ManyToMany(mappedBy = "directors")
    private Set<Movie> directedMovies = new HashSet<>();

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> actsInMovies = new HashSet<>();

    public Person() {}

    public Person(String name, LocalDate dateOfBirth, String bio) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

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
        movie.addDirector(this);
    }

    /**
     * Adds connection to movie the person acted in.
     *
     * @param movie in which the person acted
     */
    public void addActsInMovie(Movie movie) {
        actsInMovies.add(movie);
        movie.addActor(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (!getName().equals(person.getName())) return false;
        if (getDateOfBirth() != null ? !getDateOfBirth().equals(person.getDateOfBirth()) : person.getDateOfBirth() != null)
            return false;
        return getBio() != null ? getBio().equals(person.getBio()) : person.getBio() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                getDateOfBirth(),
                getBio());
    }
}
