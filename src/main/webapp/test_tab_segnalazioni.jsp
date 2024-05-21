<%@ page import="java.sql.*" %>
<%
    String connectionURL = "jdbc:mysql://localhost:3306/caf?useLegacyDatetimeCode=false&serverTimezone=Europe/Rome";
    Connection connection;
    Statement statement;
    ResultSet rs;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Test DB</title>
</head>
<body>
<%
    Class.forName("com.mysql.cj.jdbc.Driver");
    connection = DriverManager.getConnection(connectionURL, "root", "root");
    statement = connection.createStatement();
    rs = statement.executeQuery("SELECT * FROM segnalazioni");
    while (rs.next()) {
%>
<%= rs.getString("id_segnalazione") + ") "%>
<%= rs.getString("cod_impianto") + ") "%>
<%= rs.getString("cod_palinsesto") + ") "%>
<%= rs.getString("cod_cartellone") + ") "%>
<%= rs.getInt("durata_visual") + ", "%>
<%= rs.getTimestamp("data_inserimento") + "<br>"%>
<%
    }
    rs.close();
    statement.close();
    connection.close();
%>
</body>
</html>