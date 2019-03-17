package org.fmi.movietracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Similarity.
 */
@Entity
@Table(name = "similarity")
public class Similarity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_value", precision = 10, scale = 2)
    private BigDecimal value;

    @ManyToOne
    @JsonIgnoreProperties("similarities")
    private Movie movieA;

    @ManyToOne
    @JsonIgnoreProperties("similarities")
    private Movie movieB;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Similarity value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Movie getMovieA() {
        return movieA;
    }

    public Similarity movieA(Movie movie) {
        this.movieA = movie;
        return this;
    }

    public void setMovieA(Movie movie) {
        this.movieA = movie;
    }

    public Movie getMovieB() {
        return movieB;
    }

    public Similarity movieB(Movie movie) {
        this.movieB = movie;
        return this;
    }

    public void setMovieB(Movie movie) {
        this.movieB = movie;
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
        Similarity similarity = (Similarity) o;
        if (similarity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), similarity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Similarity{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
