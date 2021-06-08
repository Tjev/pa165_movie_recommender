package cz.muni.fi.pa165.dto.person;

import cz.muni.fi.pa165.dto.movie.MovieDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for Person entity with relationships to other DTOs.
 *
 * @author Jiri Papousek
 */
public class PersonDetailedDTO {

    @NotNull
    private Long id;

    @Size(max=255)
    private String name;

    @Past
    private LocalDate dateOfBirth;

    @Size(max=511)
    private String bio;

    private ArrayList<MovieDTO> directedMovies;
    private ArrayList<MovieDTO> actsInMovies;

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

    public ArrayList<MovieDTO> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(ArrayList<MovieDTO> directedMovies) {
        this.directedMovies = directedMovies;
    }

    public ArrayList<MovieDTO> getActsInMovies() {
        return actsInMovies;
    }

    public void setActsInMovies(ArrayList<MovieDTO> actsInMovies) {
        this.actsInMovies = actsInMovies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDetailedDTO)) return false;

        PersonDetailedDTO that = (PersonDetailedDTO) o;

        if (!getName().equals(that.getName())) return false;
        if (getDateOfBirth() != null ? !getDateOfBirth().equals(that.getDateOfBirth()) : that.getDateOfBirth() != null)
            return false;
        return getBio() != null ? getBio().equals(that.getBio()) : that.getBio() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                getDateOfBirth(),
                getBio());
    }

    @Override
    public String toString() {
        return "PersonDetailedDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                '}';
    }
}
