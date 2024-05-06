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
    <label for="idsegnalazione">ID Segnalazione:</label>
    <input type="text" id="idsegnalazione" name="idsegnalazione"><br><br>

    <label for="idimpianto">ID Impianto:</label>
    <input type="text" id="idimpianto" name="idimpianto"><br><br>

    <label for="idpalinsesto">ID Palinsesto:</label>
    <input type="text" id="idpalinsesto" name="idpalinsesto"><br><br>

    <label for="idcontenuto">ID Contenuto:</label>
    <input type="text" id="idcontenuto" name="idcontenuto"><br><br>

    <label for="durata">Durata:</label>
    <input type="text" id="durata" name="durata"><br><br>

    <input type="submit" value="Invia">
</form>
</body>
</html>
