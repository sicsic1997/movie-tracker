package org.fmi.movietracker.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Word.
 */
@Entity
@Table(name = "word")
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "frequency", nullable = false)
    private Integer frequency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public Word value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public Word frequency(Integer frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word = (Word) o;
        if (word.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), word.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Word{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", frequency=" + getFrequency() +
            "}";
    }
}
