package com.example.progettowsdaee;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnection {
    public static Connection getConnection() {
        Context ctx;
        DataSource ds;
        Connection connection;
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/caf");
            connection = ds.getConnection();
        } catch (Exception exc) {
            connection = null;
        }
        return connection;
    }
}
