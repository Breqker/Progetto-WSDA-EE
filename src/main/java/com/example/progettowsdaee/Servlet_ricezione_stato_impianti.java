package com.example.progettowsdaee;
import jakarta.servlet.RequestDispatcher;
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

@WebServlet(name = "Servlet_ricezione_stato_impianti", urlPatterns = "/monitoraggio")
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
            String password = "root";

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String query = "INSERT INTO impianto (idimpianto, descrizione, latitudine, longitudine) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(query);

                statement.setInt(1, impianto.getIdImpianto());
                statement.setBoolean(2, impianto.getDescrizione());
                statement.setDouble(3, impianto.getLatitudine());
                statement.setDouble(4, impianto.getLongitudine());

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Inoltra la richiesta al tuo file JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("monitoraggio.jsp");
        dispatcher.forward(request, response);
        }
    }






