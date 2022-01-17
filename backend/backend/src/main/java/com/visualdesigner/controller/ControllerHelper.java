package com.visualdesigner.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Helper class for functions which are needed in one or more controller related classes.
 */
public final class ControllerHelper {

    private ControllerHelper() {
    } // hide the constructor

    /**
     * @param statement
     * @param values
     * @throws SQLException
     */
    public static void setValues(PreparedStatement statement, Object... values)
            throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
    }
}
