package org.fmi.movietracker.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MovieGenreMapping entity.
 */
public class MovieGenreMappingDTO implements Serializable {

    private Long id;


    private Long genreId;

    private String genreCode;

    private Long movieId;

    private String movieTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getGenreCode() {
        return genreCode;
    }

    public void setGenreCode(String genreCode) {
        this.genreCode = genreCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovieGenreMappingDTO movieGenreMappingDTO = (MovieGenreMappingDTO) o;
        if (movieGenreMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieGenreMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieGenreMappingDTO{" +
            "id=" + getId() +
            ", genre=" + getGenreId() +
            ", genre='" + getGenreCode() + "'" +
            ", movie=" + getMovieId() +
            ", movie='" + getMovieTitle() + "'" +
            "}";
    }
}
