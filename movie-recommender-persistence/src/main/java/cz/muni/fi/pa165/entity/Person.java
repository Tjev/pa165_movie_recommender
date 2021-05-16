package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
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
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "bio", length = 511)
    private String bio;

    @ManyToMany(mappedBy = "directors")
    @Column(name = "directed_movies")
    private Set<Movie> directedMovies = new HashSet<>();

    @ManyToMany(mappedBy = "actors")
    @Column(name = "acts_in_movies")
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Movie> getDirectedMovies() {
        return Collections.unmodifiableSet(directedMovies);
    }

    /**
     * Adds connection to movie that has been directed by the person.
     *
     * @param movie that has been directed by this person
     */
    public void addDirectedMovie(Movie movie) {
        movie.addDirector(this);
        directedMovies.add(movie);
    }

    public Set<Movie> getActsInMovies() {
        return Collections.unmodifiableSet(actsInMovies);
    }

    /**
     * Adds connection to movie the person acted in.
     *
     * @param movie in which the person acted
     */
    public void addActsInMovie(Movie movie) {
        movie.addActor(this);
        actsInMovies.add(movie);
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                '}';
    }
}
