<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.google.gson.Gson" %>
<%@ page import="com.example.progettowsdaee.Oggetti.ImpiantoWithStatus" %>
<%@ page import="com.example.progettowsdaee.Oggetti.ImpiantiService" %>
<%@ page import="java.sql.SQLException" %>

<%
    List<ImpiantoWithStatus> impiantiWithStatus = new ArrayList<>();

    try {
        ImpiantiService service = new ImpiantiService();
        impiantiWithStatus = service.getImpiantiWithStatus();
    } catch (SQLException e) {
        e.printStackTrace();
    }

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
<header>
    <h1>Mappa degli Impianti</h1>
</header>
<div id="map-container">
    <div id="map"></div>
    <div id="legend">
        <h2>Legenda</h2>
        <p><img src="immagini/switch-on.png" alt="Attivo" width="30" height="30"> Impianto Attivo</p>
        <p><img src="immagini/switch-off.png" alt="Non Attivo" width="30" height="30"> Impianto Non Attivo</p>
    </div>
</div>
<script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
<script src="monitoraggio.js"></script>
<script>
    var impiantiWithStatusJson = JSON.parse('<%= impiantiWithStatusJson %>'.replace(/&quot;/g, '\"'));
</script>
</body>
</html>
