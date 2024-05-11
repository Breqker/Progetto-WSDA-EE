package com.example.progettowsdaee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/receiveSignal")
public class SignalReceiverServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idImpiantoParam = request.getParameter("idimpianto");
        String idPalinsestoParam = request.getParameter("idpalinsesto");
        String idCartelloneParam = request.getParameter("idcartellone");
        String durataParam = request.getParameter("durata");

        // Validazione dei parametri
        if (idImpiantoParam == null || idPalinsestoParam == null || idCartelloneParam == null || durataParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Errore: Parametri mancanti");
            return;
        }

        int idImpianto, idPalinsesto, idCartellone, durata;
        try {
            idImpianto = Integer.parseInt(idImpiantoParam);
            idPalinsesto = Integer.parseInt(idPalinsestoParam);
            idCartellone = Integer.parseInt(idCartelloneParam);
            durata = Integer.parseInt(durataParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Errore: Parametri non validi");
            return;
        }

        // Database connection and insertion logic
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDatabase", "user", "password")) {
            String sql = "INSERT INTO segnalazioni (idimpianto, idpalinsesto, idcartellone, durata, timestamp) VALUES (?, ?, ?, ?, now())";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idImpianto);
                stmt.setInt(2, idPalinsesto);
                stmt.setInt(3, idCartellone);
                stmt.setInt(4, durata);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            // Qui potresti implementare una gestione più sofisticata delle eccezioni
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Errore durante l'inserimento nel database");
            return;
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Signal Received");
    }
}
