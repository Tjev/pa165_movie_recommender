package cz.fi.muni.pa165.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for Person entity with relationships to other DTOs.
 *
 * @author Jiri Papousek
 */
public class PersonDetailedDTO {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String bio;

    private Set<MovieDTO> directedMovies;
    private Set<MovieDTO> actsInMovies;

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

    public Set<MovieDTO> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(Set<MovieDTO> directedMovies) {
        this.directedMovies = directedMovies;
    }

    public Set<MovieDTO> getActsInMovies() {
        return actsInMovies;
    }

    public void setActsInMovies(Set<MovieDTO> actsInMovies) {
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
