package org.fmi.movietracker.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MovieLanguageMapping entity.
 */
public class MovieLanguageMappingDTO implements Serializable {

    private Long id;


    private Long movieId;

    private String movieTitle;

    private Long languageId;

    private String languageCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovieLanguageMappingDTO movieLanguageMappingDTO = (MovieLanguageMappingDTO) o;
        if (movieLanguageMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieLanguageMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieLanguageMappingDTO{" +
            "id=" + getId() +
            ", movie=" + getMovieId() +
            ", movie='" + getMovieTitle() + "'" +
            ", language=" + getLanguageId() +
            ", language='" + getLanguageCode() + "'" +
            "}";
    }
}
