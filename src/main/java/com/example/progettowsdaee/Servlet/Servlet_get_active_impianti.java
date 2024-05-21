package com.example.progettowsdaee.Servlet;

import com.example.progettowsdaee.Oggetti.Impianto;
import com.example.progettowsdaee.Oggetti.ImpiantoWithStatus;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "Servlet_get_active_and_inactive_impianti", urlPatterns = "/getStatusImpianti")
public class Servlet_get_active_impianti extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Connection connection = null;

        List<Impianto> allImpianti = new ArrayList<>();
        Set<String> activeImpianti = new HashSet<>();

        try {
            connection = DBConnection.getConnection();

            // Query per ottenere tutti gli impianti
            String allImpiantiQuery = "SELECT * FROM caf.impianto";
            PreparedStatement allImpiantiStatement = connection.prepareStatement(allImpiantiQuery);
            ResultSet allImpiantiResultSet = allImpiantiStatement.executeQuery();

            while (allImpiantiResultSet.next()) {
                Impianto impianto = new Impianto();
                impianto.setIdImpianto(allImpiantiResultSet.getString("id_impianto"));
                impianto.setIdPalinsesto(allImpiantiResultSet.getString("id_palinsesto"));
                impianto.setLatitudine(allImpiantiResultSet.getDouble("latitudine"));
                impianto.setLongitudine(allImpiantiResultSet.getDouble("longitudine"));
                allImpianti.add(impianto);
            }

            // Query per ottenere gli impianti attivi negli ultimi 5 minuti
            String activeImpiantiQuery = "SELECT i.id_impianto, i.id_palinsesto " +
                    "FROM caf.impianto i " +
                    "JOIN caf.segnalazioni s ON i.id_impianto = s.cod_impianto AND i.id_palinsesto = s.cod_palinsesto " +
                    "WHERE s.data_inserimento >= NOW() - INTERVAL 5 MINUTE";
            PreparedStatement activeImpiantiStatement = connection.prepareStatement(activeImpiantiQuery);
            ResultSet activeImpiantiResultSet = activeImpiantiStatement.executeQuery();

            while (activeImpiantiResultSet.next()) {
                String idImpianto = activeImpiantiResultSet.getString("id_impianto");
                String idPalinsesto = activeImpiantiResultSet.getString("id_palinsesto");
                activeImpianti.add(idImpianto + "-" + idPalinsesto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<ImpiantoWithStatus> impiantiWithStatus = new ArrayList<>();
        for (Impianto impianto : allImpianti) {
            ImpiantoWithStatus impiantoWithStatus = new ImpiantoWithStatus();
            impiantoWithStatus.setIdImpianto(impianto.getIdImpianto());
            impiantoWithStatus.setIdPalinsesto(impianto.getIdPalinsesto());
            impiantoWithStatus.setLatitudine(impianto.getLatitudine());
            impiantoWithStatus.setLongitudine(impianto.getLongitudine());
            String key = impianto.getIdImpianto() + "-" + impianto.getIdPalinsesto();
            impiantoWithStatus.setActive(activeImpianti.contains(key));
            impiantiWithStatus.add(impiantoWithStatus);
        }

        String impiantiJson = new Gson().toJson(impiantiWithStatus);
        response.getWriter().write(impiantiJson);
    }
}
