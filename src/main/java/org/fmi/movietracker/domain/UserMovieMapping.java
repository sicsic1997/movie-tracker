package org.fmi.movietracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserMovieMapping.
 */
@Entity
@Table(name = "user_movie_mapping")
public class UserMovieMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("userMovieMappings")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("userMovieMappings")
    private Movie movie;

    @ManyToOne
    @JsonIgnoreProperties("userMovieMappings")
    private MovieStatus movieStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public UserMovieMapping user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public UserMovieMapping movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieStatus getMovieStatus() {
        return movieStatus;
    }

    public UserMovieMapping movieStatus(MovieStatus movieStatus) {
        this.movieStatus = movieStatus;
        return this;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        this.movieStatus = movieStatus;
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
        UserMovieMapping userMovieMapping = (UserMovieMapping) o;
        if (userMovieMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMovieMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMovieMapping{" +
            "id=" + getId() +
            "}";
    }
}
