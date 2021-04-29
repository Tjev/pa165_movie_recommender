package cz.fi.muni.pa165.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for Person entity (for creation purposes).
 *
 * @author Jiri Papousek
 */
public class PersonCreateDto {

    private String name;
    private LocalDate dateOfBirth;
    private String bio;

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
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                '}';
    }
}