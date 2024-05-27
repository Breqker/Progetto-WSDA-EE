package com.example.progettowsdaee.Servlet;

import com.example.progettowsdaee.database.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.sql.*;

@WebServlet(name = "Servlet_ricezione_stato_impianti", urlPatterns = "/monitoraggio_servlet")
public class Servlet_ricezione_stato_impianti extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();

        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();


        String idSegnalazione = jsonObject.getString("idSegnalazione");
        String codImpianto = jsonObject.getString("codImpianto");
        String codPalinsesto = jsonObject.getString("codPalinsesto");
        String codCartellone = jsonObject.getString("codCartellone");
        int durataVisual = jsonObject.getInt("durataVisual");

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            String query = "INSERT INTO segnalazioni (id_segnalazione, cod_impianto, cod_palinsesto, cod_cartellone, durata_visual) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, idSegnalazione);
            statement.setString(2, codImpianto);
            statement.setString(3, codPalinsesto);
            statement.setString(4, codCartellone);
            statement.setInt(5, durataVisual);

            statement.executeUpdate();

            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.println("Segnalazione ricevuta e salvata con successo");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'accesso al database: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
