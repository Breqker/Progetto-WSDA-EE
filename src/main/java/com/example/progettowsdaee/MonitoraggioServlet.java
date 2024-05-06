package com.example.progettowsdaee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "MonitoraggioServlet", urlPatterns = "/monitoraggio")
public class MonitoraggioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests here if needed
        // For example, you can forward to a JSP page
        request.getRequestDispatcher("monitoraggio.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera i parametri dalla richiesta HTTP
        int idSegnalazione = Integer.parseInt(request.getParameter("idsegnalazione"));
        String idImpianto = request.getParameter("idimpianto");
        String idPalinsesto = request.getParameter("idpalinsesto");
        String idContenuto = request.getParameter("idcontenuto");
        int durata = Integer.parseInt(request.getParameter("durata"));

        // Ottieni una connessione al database utilizzando la classe DBConnection
        try (Connection conn = DBConnection.getConnection()) {
            // Query per inserire i dati nella tabella monitoraggio
            String sql = "INSERT INTO caf.visualizzazione (idsegnalazione, idimpianto, idpalinsesto, idcontenuto, durata, dataora) VALUES (?, ?, ?, ?, ?, NOW())";

            // Prepara lo statement SQL
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Imposta i parametri dello statement
                statement.setInt(1, idSegnalazione);
                statement.setString(2, idImpianto);
                statement.setString(3, idPalinsesto);
                statement.setString(4, idContenuto);
                statement.setInt(5, durata);

                // Esegue l'inserimento dei dati
                statement.executeUpdate();
            }

            // Reindirizza alla pagina JSP dopo aver memorizzato i dati
            response.sendRedirect("monitoraggio.jsp");
        } catch (SQLException e) {
            // Gestione degli errori
            e.printStackTrace();
            response.getWriter().println("Errore durante l'accesso al database: " + e.getMessage());
        }
    }
}
