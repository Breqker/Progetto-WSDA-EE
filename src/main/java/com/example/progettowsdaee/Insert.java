package com.example.progettowsdaee;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "insertServlet", value = "/insert")
public class Insert extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection;
        PreparedStatement statement;

        try {
            connection = DBConnectionDatasource.getConnection();

            String insert = "insert into student(id, firstname, lastname) values (?,?,?)";
            statement = connection.prepareStatement(insert);
            statement.setInt(1, Integer.parseInt(request.getParameter("id")));
            statement.setString(2, request.getParameter("firstname"));
            statement.setString(3, request.getParameter("lastname"));
            statement.executeUpdate();

            statement.close();
            connection.close();
            response.sendRedirect("testdb-datasource");
        } catch (Exception exc) {
            out.println(exc.getMessage());
        }
    }
}