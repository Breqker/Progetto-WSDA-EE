<%@ page import="java.sql.*" %>
<%
    String connectionURL = "jdbc:mysql://localhost:3306/mlc?useLegacyDatetimeCode=false&serverTimezone=Europe/Rome";
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
    connection = DriverManager.getConnection(connectionURL, "root", "PA846645");
    statement = connection.createStatement();
    rs = statement.executeQuery("SELECT * FROM student");
    while (rs.next()) {
%>
<%= rs.getString("id") + ") " + rs.getString("firstname") + ", " + rs.getString("lastname") + "<br/>" %>
<%
    }
    rs.close();
    statement.close();
    connection.close();
%>
</body>
</html>