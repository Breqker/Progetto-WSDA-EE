package com.example.progettowsdaee;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "deleteServlet", value = "/delete")
public class Delete extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection;
        PreparedStatement statement;

        try {
            connection = DBConnection.getConnection();

            String delete = "delete from student where id=?";
            statement = connection.prepareStatement(delete);

            statement.setInt(1, Integer.parseInt(request.getParameter("queryid")));
            statement.executeUpdate();

            statement.close();
            connection.close();
            response.sendRedirect("testdb-datasource");
        } catch (Exception exc) {
            out.println(exc.getMessage());
        }
    }
}