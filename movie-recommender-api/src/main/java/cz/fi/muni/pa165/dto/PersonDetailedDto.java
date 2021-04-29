package cz.fi.muni.pa165.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for Person entity with relationships to other DTOs.
 *
 * @author Jiri Papousek
 */
public class PersonDetailedDto {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String bio;

    private List<MovieDto> directedMovies;
    private List<MovieDto> actsInMovies;

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

    public List<MovieDto> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(List<MovieDto> directedMovies) {
        this.directedMovies = directedMovies;
    }

    public List<MovieDto> getActsInMovies() {
        return actsInMovies;
    }

    public void setActsInMovies(List<MovieDto> actsInMovies) {
        this.actsInMovies = actsInMovies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDto)) return false;

        PersonDto personDto = (PersonDto) o;

        if (!getName().equals(personDto.getName())) return false;
        if (getDateOfBirth() != null ? !getDateOfBirth().equals(personDto.getDateOfBirth()) : personDto.getDateOfBirth() != null)
            return false;
        return getBio() != null ? getBio().equals(personDto.getBio()) : personDto.getBio() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                getDateOfBirth(),
                getBio());
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                '}';
    }
}