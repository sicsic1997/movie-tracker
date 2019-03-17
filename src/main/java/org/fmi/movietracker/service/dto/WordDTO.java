package org.fmi.movietracker.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Word entity.
 */
public class WordDTO implements Serializable {

    private Long id;

    @NotNull
    private String value;

    @NotNull
    private Integer frequency;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WordDTO wordDTO = (WordDTO) o;
        if (wordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WordDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", frequency=" + getFrequency() +
            "}";
    }
}
