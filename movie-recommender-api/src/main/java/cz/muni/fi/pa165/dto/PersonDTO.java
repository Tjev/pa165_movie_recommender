package cz.muni.fi.pa165.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for Person entity.
 *
 * @author Jiri Papousek
 */
public class PersonDTO {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String bio;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDTO)) return false;

        PersonDTO personDTO = (PersonDTO) o;

        if (!getName().equals(personDTO.getName())) return false;
        if (getDateOfBirth() != null ? !getDateOfBirth().equals(personDTO.getDateOfBirth()) : personDTO.getDateOfBirth() != null)
            return false;
        return getBio() != null ? getBio().equals(personDTO.getBio()) : personDTO.getBio() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                getDateOfBirth(),
                getBio());
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                '}';
    }
}
