package org.fmi.movietracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MoviePeopleRoleMapping.
 */
@Entity
@Table(name = "movie_people_role_mapping")
public class MoviePeopleRoleMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("moviePeopleRoleMappings")
    private People people;

    @ManyToOne
    @JsonIgnoreProperties("peopleList(name)S")
    private Movie movie;

    @ManyToOne
    @JsonIgnoreProperties("moviePeopleRoleMappings")
    private Role role;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public People getPeople() {
        return people;
    }

    public MoviePeopleRoleMapping people(People people) {
        this.people = people;
        return this;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public Movie getMovie() {
        return movie;
    }

    public MoviePeopleRoleMapping movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Role getRole() {
        return role;
    }

    public MoviePeopleRoleMapping role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
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
        MoviePeopleRoleMapping moviePeopleRoleMapping = (MoviePeopleRoleMapping) o;
        if (moviePeopleRoleMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moviePeopleRoleMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoviePeopleRoleMapping{" +
            "id=" + getId() +
            "}";
    }
}
