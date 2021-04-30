package cz.muni.fi.pa165.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for Person entity (for creation purposes).
 *
 * @author Jiri Papousek
 */
public class PersonCreateDTO {

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
        if (!(o instanceof PersonCreateDTO)) return false;

        PersonCreateDTO that = (PersonCreateDTO) o;

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
        return "PersonCreateDTO{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                '}';
    }
}
