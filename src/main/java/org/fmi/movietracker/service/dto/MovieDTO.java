package org.fmi.movietracker.service.dto;
import org.fmi.movietracker.domain.Language;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Movie entity.
 */
public class MovieDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Integer year;

    private String released;

    private Integer runtime;

    @Size(max = 2000)
    private String plot;

    @Size(max = 2000)
    private String poster;

    private BigDecimal rating;


    private Long ratedId;

    private String ratedCode;

    private Long productionId;

    private String productionCode;

    private Set<MovieGenreMappingDTO> genreLists;

    private Set<MoviePeopleRoleMappingDTO> peopleLists;

    private Set<MovieLanguageMappingDTO> languageLists;

    public Set<MovieGenreMappingDTO> getGenreLists() {
        return genreLists;
    }

    public void setGenreLists(Set<MovieGenreMappingDTO> genreLists) {
        this.genreLists = genreLists;
    }

    public Set<MoviePeopleRoleMappingDTO> getPeopleLists() {
        return peopleLists;
    }

    public void setPeopleLists(Set<MoviePeopleRoleMappingDTO> peopleLists) {
        this.peopleLists = peopleLists;
    }

    public Set<MovieLanguageMappingDTO> getLanguageLists() {
        return languageLists;
    }

    public void setLanguageLists(Set<MovieLanguageMappingDTO> languageLists) {
        this.languageLists = languageLists;
    }

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

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
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
        return Objects.hash(id, title, year, released, runtime, plot, poster, rating, ratedId, ratedCode, productionId, productionCode, genreLists, peopleLists, languageLists);
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", year=" + year +
            ", released='" + released + '\'' +
            ", runtime=" + runtime +
            ", plot='" + plot + '\'' +
            ", poster='" + poster + '\'' +
            ", rating=" + rating +
            ", ratedId=" + ratedId +
            ", ratedCode='" + ratedCode + '\'' +
            ", productionId=" + productionId +
            ", productionCode='" + productionCode + '\'' +
            ", genreDTOSet=" + genreLists +
            ", peopleDTOS=" + peopleLists +
            ", languageDTOS=" + languageLists +
            '}';
    }
}
