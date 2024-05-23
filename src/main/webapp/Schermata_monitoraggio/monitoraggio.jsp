<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, java.util.*, com.google.gson.Gson" %>
<%@ page import="com.example.progettowsdaee.Oggetti.ImpiantoWithStatus" %>
<%@ page import="com.example.progettowsdaee.database.DBConnection" %>
<%@ page import="com.example.progettowsdaee.Oggetti.Impianto" %>

<%
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

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Converti impiantiWithStatus in JSON
    String impiantiWithStatusJson = new Gson().toJson(impiantiWithStatus);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mappa degli Impianti</title>
    <link rel="stylesheet" type="text/css" href="monitoraggio.css">
    <link rel="stylesheet" type="text/css" href="https://unpkg.com/leaflet/dist/leaflet.css" />
</head>
<body>
<div id="map"></div>
<script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
<script src="monitoraggio.js"></script>
<script> var impiantiWithStatusJson = <%= impiantiWithStatusJson %>;</script>
</body>
</html>
