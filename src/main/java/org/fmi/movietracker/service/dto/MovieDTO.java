package org.fmi.movietracker.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Movie entity.
 */
public class MovieDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    @Min(value = 1900)
    private Integer year;

    private Instant released;

    private Integer runtime;

    private String plot;

    private String poster;

    private BigDecimal rating;


    private Long ratedId;

    private String ratedCode;

    private Long productionId;

    private String productionCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Instant getReleased() {
        return released;
    }

    public void setReleased(Instant released) {
        this.released = released;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Long getRatedId() {
        return ratedId;
    }

    public void setRatedId(Long ratedId) {
        this.ratedId = ratedId;
    }

    public String getRatedCode() {
        return ratedCode;
    }

    public void setRatedCode(String ratedCode) {
        this.ratedCode = ratedCode;
    }

    public Long getProductionId() {
        return productionId;
    }

    public void setProductionId(Long productionId) {
        this.productionId = productionId;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovieDTO movieDTO = (MovieDTO) o;
        if (movieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", year=" + getYear() +
            ", released='" + getReleased() + "'" +
            ", runtime=" + getRuntime() +
            ", plot='" + getPlot() + "'" +
            ", poster='" + getPoster() + "'" +
            ", rating=" + getRating() +
            ", rated=" + getRatedId() +
            ", rated='" + getRatedCode() + "'" +
            ", production=" + getProductionId() +
            ", production='" + getProductionCode() + "'" +
            "}";
    }
}
