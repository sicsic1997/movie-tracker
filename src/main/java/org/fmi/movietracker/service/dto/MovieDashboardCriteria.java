package org.fmi.movietracker.service.dto;

import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

public class MovieDashboardCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private StringFilter filter;

    @Override
    public String
    toString() {
        return "MovieDashboardCriteria{" +
            "filter=" + filter +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDashboardCriteria that = (MovieDashboardCriteria) o;
        return Objects.equals(filter, that.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public StringFilter getFilter() {
        return filter;
    }

    public void setFilter(StringFilter filter) {
        this.filter = filter;
    }
}
