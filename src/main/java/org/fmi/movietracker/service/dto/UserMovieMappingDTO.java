package org.fmi.movietracker.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserMovieMapping entity.
 */
public class UserMovieMappingDTO implements Serializable {

    private Long id;


    private Long userId;

    private String userLogin;

    private Long movieId;

    private String movieTitle;

    private Long movieStatusId;

    private String movieStatusCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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

    public Long getMovieStatusId() {
        return movieStatusId;
    }

    public void setMovieStatusId(Long movieStatusId) {
        this.movieStatusId = movieStatusId;
    }

    public String getMovieStatusCode() {
        return movieStatusCode;
    }

    public void setMovieStatusCode(String movieStatusCode) {
        this.movieStatusCode = movieStatusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserMovieMappingDTO userMovieMappingDTO = (UserMovieMappingDTO) o;
        if (userMovieMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMovieMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMovieMappingDTO{" +
            "id=" + getId() +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", movie=" + getMovieId() +
            ", movie='" + getMovieTitle() + "'" +
            ", movieStatus=" + getMovieStatusId() +
            ", movieStatus='" + getMovieStatusCode() + "'" +
            "}";
    }
}
