package org.fmi.movietracker.domain;

public enum RoleCode {

    DIRECTOR("Director"), WRITER("Writer"), ACTOR("Actor");

    private String code;

    RoleCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
