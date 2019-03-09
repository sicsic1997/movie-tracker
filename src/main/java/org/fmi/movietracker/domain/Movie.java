package org.fmi.movietracker.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotNull
    @Min(value = 1900)
    @Column(name = "jhi_year", nullable = false)
    private Integer year;

    @Column(name = "released")
    private Instant released;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "plot")
    private String plot;

    @Column(name = "poster")
    private String poster;

    @Column(name = "rating", precision = 10, scale = 2)
    private BigDecimal rating;

    @OneToOne
    @JoinColumn(unique = true)
    private Rated rated;

    @OneToOne
    @JoinColumn(unique = true)
    private Production production;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Movie title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public Movie year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Instant getReleased() {
        return released;
    }

    public Movie released(Instant released) {
        this.released = released;
        return this;
    }

    public void setReleased(Instant released) {
        this.released = released;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public Movie runtime(Integer runtime) {
        this.runtime = runtime;
        return this;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getPlot() {
        return plot;
    }

    public Movie plot(String plot) {
        this.plot = plot;
        return this;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public Movie poster(String poster) {
        this.poster = poster;
        return this;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public Movie rating(BigDecimal rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Rated getRated() {
        return rated;
    }

    public Movie rated(Rated rated) {
        this.rated = rated;
        return this;
    }

    public void setRated(Rated rated) {
        this.rated = rated;
    }

    public Production getProduction() {
        return production;
    }

    public Movie production(Production production) {
        this.production = production;
        return this;
    }

    public void setProduction(Production production) {
        this.production = production;
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
        Movie movie = (Movie) o;
        if (movie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", year=" + getYear() +
            ", released='" + getReleased() + "'" +
            ", runtime=" + getRuntime() +
            ", plot='" + getPlot() + "'" +
            ", poster='" + getPoster() + "'" +
            ", rating=" + getRating() +
            "}";
    }
}
