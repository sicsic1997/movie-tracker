package org.fmi.movietracker.domain;

public enum MovieStatusCode {

    WISH_LIST("WISH_LIST"), HISTORY("HISTORY");

    private String code;

    MovieStatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
