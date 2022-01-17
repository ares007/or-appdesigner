package com.visualdesigner.api;

/**
 * This class was made to fix the magicnumber error.
 * When trying to build with github actions.
 * This is just an list for the HTTP header responses.
 */
public enum ResponseNumber {

    /**
     * returns 404, not found.
     */
    NOTFOUND(404),
    /**
     * returns 405, Method not allowed.
     */
    NOTALLOWED(405),
    /**
     * returns 200, Ok.
     */
    OK(200),
    /**
     * returns 500, Internal Server Error.
     */
    INTERNALERROR(500),
    /**
     * returns 401, Unauthorized.
     */
    UNAUTHORIZED(401),
    /**
     * returns 204, No content.
     */
    NOCONTENT(204);

    private final int values;

    ResponseNumber(int values) {
        this.values = values;
    }

    public int getValues() {
        return values;
    }
}
