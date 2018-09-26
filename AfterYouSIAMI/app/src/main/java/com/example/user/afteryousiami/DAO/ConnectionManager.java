package com.example.user.afteryousiami.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://siami.cqxerectc3zr.us-east-2.rds.amazonaws.com/afteryou";
    private static final String USERNAME = "siami2018";
    private static final String PASSWORD = "S1am12018";

    static {
        try {
            Class.forName(DRIVER_NAME).newInstance();

        } catch (InstantiationException ie) {
            printError(ie);
        } catch (IllegalAccessException ille) {
            printError(ille);
        } catch (ClassNotFoundException ex) {
            printError(ex);
        }
    }

    /***
     * initiates a connection to the aws database
     * @return Connection object
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /***
     * closes the connection
     * @param conn null if absent
     * @param stmt null if absent
     * @param rs null if absent
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void printDebug(String message) {
        Log.d("ConnectionManager.java", message);
    }

    private static void printError(Exception e) {
        Log.e("ConnectionManager.java", "error", e);
    }
}
