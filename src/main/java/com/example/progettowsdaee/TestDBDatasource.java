package com.example.progettowsdaee;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "testDBDatasourceServlet", value = "/testdb-datasource")
public class TestDBDatasource extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            connection = DBConnectionDatasource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM impianto");
            while (rs.next()) {
                out.print(rs.getString("idimpianto") + ") ");
                out.println(rs.getString("descrizione") + ", ");
                out.println(rs.getBigDecimal("latitudine") + ", ");
                out.println(rs.getBigDecimal("longitudine") + "<br>");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (Exception exc) {
            out.println(exc.getMessage());
        }
    }
}