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
import java.sql.Timestamp;
import java.io.PrintWriter;

@WebServlet(name = "MonitoraggioServlet", value = "/monitoraggio")
public class MonitoraggioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Genera il form HTML
        generateForm(response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera i parametri dalla richiesta HTTP
        int idSegnalazione = Integer.parseInt(request.getParameter("idsegnalazione"));
        String idImpianto = request.getParameter("idimpianto");
        String idPalinsesto = request.getParameter("idpalinsesto");
        String idContenuto = request.getParameter("idcontenuto");
        int durata = Integer.parseInt(request.getParameter("durata"));
        Timestamp dataOra = Timestamp.valueOf(request.getParameter("dataora"));

        // Ottieni una connessione al database utilizzando la classe DBConnection
        try (Connection conn = DBConnection.getConnection()) {
            // Query per inserire i dati nella tabella monitoraggio
            String sql = "INSERT INTO caf.visualizzazione (idsegnalazione, idimpianto, idpalinsesto, idcontenuto, durata, dataora) VALUES (?, ?, ?, ?, ?, ?)";

            // Prepara lo statement SQL
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Imposta i parametri dello statement
                statement.setInt(1, idSegnalazione);
                statement.setString(2, idImpianto);
                statement.setString(3, idPalinsesto);
                statement.setString(4, idContenuto);
                statement.setInt(5, durata);
                statement.setTimestamp(6, dataOra);

                // Esegue l'inserimento dei dati
                statement.executeUpdate();
            }

            // Invia una risposta di successo
            response.getWriter().println("Dati memorizzati con successo.");
        } catch (SQLException e) {
            // Gestione degli errori
            e.printStackTrace();
            response.getWriter().println("Errore durante l'accesso al database: " + e.getMessage());
        }
    }

    // Metodo per generare il form HTML
    private void generateForm(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Form di Monitoraggio</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Form di Monitoraggio</h1>");
        out.println("<form action=\"monitoraggio\" method=\"post\">");
        out.println("  <label for=\"idsegnalazione\">ID Segnalazione:</label>");
        out.println("  <input type=\"text\" id=\"idsegnalazione\" name=\"idsegnalazione\"><br><br>");
        out.println("  <label for=\"idimpianto\">ID Impianto:</label>");
        out.println("  <input type=\"text\" id=\"idimpianto\" name=\"idimpianto\"><br><br>");
        out.println("  <label for=\"idpalinsesto\">ID Palinsesto:</label>");
        out.println("  <input type=\"text\" id=\"idpalinsesto\" name=\"idpalinsesto\"><br><br>");
        out.println("  <label for=\"idcontenuto\">ID Contenuto:</label>");
        out.println("  <input type=\"text\" id=\"idcontenuto\" name=\"idcontenuto\"><br><br>");
        out.println("  <label for=\"durata\">Durata:</label>");
        out.println("  <input type=\"text\" id=\"durata\" name=\"durata\"><br><br>");
        out.println("  <label for=\"dataora\">Data e Ora (YYYY-MM-DD HH:MM:SS):</label>");
        out.println("  <input type=\"text\" id=\"dataora\" name=\"dataora\"><br><br>");
        out.println("  <input type=\"submit\" value=\"Invia\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}
