package com.visualdesigner.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * this class handles the database connection.
 */
public class DBConnection {
    private String user;
    private String pass;
    private String driver;
    private String host;
    private String name;
    private String dbUrl;

    private Connection conn;

    /**
     * Constructor.
     */
    public DBConnection() {

        try {
            user = removeDoubleQuotes(System.getenv("DB_USERNAME"));
            pass = System.getenv("DB_PASSWORD");
            if (System.getenv("DB_PASSWORD") != null) {
                pass = removeDoubleQuotes(System.getenv("DB_PASSWORD"));
            }
            driver = removeDoubleQuotes(System.getenv("DB_DRIVER"));
            host = removeDoubleQuotes(System.getenv("DB_HOST"));
            name = removeDoubleQuotes(System.getenv("DB_DATABASE"));
            dbUrl = driver + "://" + host + "/" + name;
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a connection to the database.
     * @return returns the connection to the database
     * @exception SQLException gets called if an error occurs
     */
    public Connection connect() {
        try {
            conn = DriverManager.getConnection(dbUrl, user, pass);
        } catch (SQLException ex) {
            System.out.println("Failed to create the database connection. " + ex);
        }
        return conn;
    }

    public String removeDoubleQuotes(String request) {
        return request.replace("\"", "");
    }

}
