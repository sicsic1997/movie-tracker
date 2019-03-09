package org.fmi.movietracker.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MoviePeopleRoleMapping entity.
 */
public class MoviePeopleRoleMappingDTO implements Serializable {

    private Long id;


    private Long peopleId;

    private String peopleName;

    private Long movieId;

    private String movieTitle;

    private Long roleId;

    private String roleCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Long peopleId) {
        this.peopleId = peopleId;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO = (MoviePeopleRoleMappingDTO) o;
        if (moviePeopleRoleMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moviePeopleRoleMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoviePeopleRoleMappingDTO{" +
            "id=" + getId() +
            ", people=" + getPeopleId() +
            ", people='" + getPeopleName() + "'" +
            ", movie=" + getMovieId() +
            ", movie='" + getMovieTitle() + "'" +
            ", role=" + getRoleId() +
            ", role='" + getRoleCode() + "'" +
            "}";
    }
}
