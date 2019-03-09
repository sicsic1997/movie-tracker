package org.fmi.movietracker.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the People entity.
 */
public class PeopleDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PeopleDTO peopleDTO = (PeopleDTO) o;
        if (peopleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), peopleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeopleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
