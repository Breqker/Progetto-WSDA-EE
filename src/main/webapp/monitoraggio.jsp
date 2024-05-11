<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form di Monitoraggio</title>
</head>
<body>
<h1>Form di Monitoraggio</h1>
<form action="monitoraggio" method="post">

    <label for="idimpianto">ID Impianto:</label>
    <input type="text" id="idimpianto" name="idImpianto"><br><br>

    <label for="descrizione">Descrizione:</label>
    <input type="text" id="descrizione" name="descrizione"><br><br>

    <label for="latitudine">Latitudine:</label>
    <input type="text" id="latitudine" name="latitudine"><br><br>

    <label for="longitudine">Longitudine:</label>
    <input type="text" id="longitudine" name="longitudine"><br><br>

    <input type="submit" value="Invia">
</form>
</body>
</html>
