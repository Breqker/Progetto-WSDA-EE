package com.example.progettowsdaee.Oggetti;

import com.example.progettowsdaee.database.DBConnection;
import java.sql.*;
import java.util.*;

public class ImpiantiService {

    public List<ImpiantoWithStatus> getImpiantiWithStatus() throws SQLException {
        Connection connection = null;
        List<ImpiantoWithStatus> impiantiWithStatus = new ArrayList<>();

        try {
            connection = DBConnection.getConnection();

            List<Impianto> allImpianti = new ArrayList<>();
            Set<String> activeImpianti = new HashSet<>();

            // Query per ottenere tutti gli impianti
            String allImpiantiQuery = "SELECT * FROM caf.impianto";
            PreparedStatement allImpiantiStatement = connection.prepareStatement(allImpiantiQuery);
            ResultSet allImpiantiResultSet = allImpiantiStatement.executeQuery();

            while (allImpiantiResultSet.next()) {
                Impianto impianto = new Impianto();
                impianto.setIdImpianto(allImpiantiResultSet.getString("id_impianto"));
                impianto.setIdPalinsesto(allImpiantiResultSet.getString("ref_palinsesto"));
                impianto.setLatitudine(allImpiantiResultSet.getDouble("latitudine"));
                impianto.setLongitudine(allImpiantiResultSet.getDouble("longitudine"));
                impianto.setStato(allImpiantiResultSet.getBoolean("stato"));
                allImpianti.add(impianto);
            }


            String activeImpiantiQuery = "SELECT i.id_impianto, i.ref_palinsesto " +
                    "FROM caf.impianto i " +
                    "JOIN caf.segnalazioni s ON i.id_impianto = s.cod_impianto AND i.ref_palinsesto = s.cod_palinsesto " +
                    "WHERE s.data_inserimento >= NOW() - INTERVAL 5 MINUTE";
            PreparedStatement activeImpiantiStatement = connection.prepareStatement(activeImpiantiQuery);
            ResultSet activeImpiantiResultSet = activeImpiantiStatement.executeQuery();

            while (activeImpiantiResultSet.next()) {
                String idImpianto = activeImpiantiResultSet.getString("id_impianto");
                String idPalinsesto = activeImpiantiResultSet.getString("ref_palinsesto");
                activeImpianti.add(idImpianto + "-" + idPalinsesto);
            }

            for (Impianto impianto : allImpianti) {
                ImpiantoWithStatus impiantoWithStatus = new ImpiantoWithStatus();
                impiantoWithStatus.setIdImpianto(impianto.getIdImpianto());
                impiantoWithStatus.setIdPalinsesto(impianto.getIdPalinsesto());
                impiantoWithStatus.setLatitudine(impianto.getLatitudine());
                impiantoWithStatus.setLongitudine(impianto.getLongitudine());
                impiantoWithStatus.setStato(impianto.getStato());
                String key = impianto.getIdImpianto() + "-" + impianto.getIdPalinsesto();
                impiantoWithStatus.setActive(activeImpianti.contains(key));
                impiantiWithStatus.add(impiantoWithStatus);
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return impiantiWithStatus;
    }
}
