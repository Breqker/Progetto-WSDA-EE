package com.example.progettowsdaee.Servlet;
import com.example.progettowsdaee.Oggetti.Impianto;
import com.example.progettowsdaee.database.DBConnection;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "Servlet_get_from_DB", urlPatterns = "/getImpianti")
public class Servlet_get_from_DB extends HttpServlet {

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_OK);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Connection connection = null;

        List<Impianto> impianti = new ArrayList<>();

        try {

            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM caf.impianto");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Impianto impianto = new Impianto();
                impianto.setIdImpianto(resultSet.getString("id_impianto"));
                impianto.setIdPalinsesto(resultSet.getString("id_palinsesto"));
                impianto.setLatitudine(resultSet.getDouble("latitudine"));
                impianto.setLongitudine(resultSet.getDouble("longitudine"));
                impianti.add(impianto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String impiantiJson = new Gson().toJson(impianti);
        response.getWriter().write(impiantiJson);
    }
}