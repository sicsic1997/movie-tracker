package org.fmi.movietracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MovieGenreMapping.
 */
@Entity
@Table(name = "movie_genre_mapping")
public class MovieGenreMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("movieGenreMappings")
    private Genre genre;

    @ManyToOne
    @JsonIgnoreProperties("genreList(code)S")
    private Movie movie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public MovieGenreMapping genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieGenreMapping movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
        MovieGenreMapping movieGenreMapping = (MovieGenreMapping) o;
        if (movieGenreMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieGenreMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieGenreMapping{" +
            "id=" + getId() +
            "}";
    }
}
