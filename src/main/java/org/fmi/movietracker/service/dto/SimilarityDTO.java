package org.fmi.movietracker.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Similarity entity.
 */
public class SimilarityDTO implements Serializable {

    private Long id;

    private BigDecimal value;


    private Long movieAId;

    private Long movieBId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getMovieAId() {
        return movieAId;
    }

    public void setMovieAId(Long movieId) {
        this.movieAId = movieId;
    }

    public Long getMovieBId() {
        return movieBId;
    }

    public void setMovieBId(Long movieId) {
        this.movieBId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimilarityDTO similarityDTO = (SimilarityDTO) o;
        if (similarityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), similarityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SimilarityDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", movieA=" + getMovieAId() +
            ", movieB=" + getMovieBId() +
            "}";
    }
}
