package com.example.progettowsdaee;
import jakarta.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name = "Servlet_ricezione_stato_impianti", urlPatterns = "/ricezione")
public class Servlet_ricezione_stato_impianti extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*"); // CORS settings

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();

        ObjectMapper mapper = new ObjectMapper();
        Impianto impianto = mapper.readValue(jsonString, Impianto.class);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/caf";
            String username = "root";
            String password = "Amedeo01";

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String query = "INSERT INTO visualizione (idimpianto, idpalinsesto, idcartellone, durata) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(query);

                statement.setString(1, impianto.getIdImpianto());
                statement.setString(2, impianto.getDescrizione());
                statement.setString(3, impianto.getDescrizione());
                statement.setString(4, impianto.getIdImpianto());

                statement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'accesso al database");
            return;
        }

            // Invia una risposta di successo al client
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.println("Segnalazione ricevuta e salvata con successo");
        }
    }






