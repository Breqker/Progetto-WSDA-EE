<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%
    Context ctx = null;
    DataSource ds = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Test DB</title>
</head>
<body>
<%
    ctx = new InitialContext();
    ds = (DataSource) ctx.lookup("java:comp/env/jdbc/caf");
    connection = ds.getConnection();
    statement = connection.createStatement();
    rs = statement.executeQuery("SELECT * FROM impianto");
    while (rs.next()) {
%>

<%= rs.getString("idimpianto") + ") "%>
<%= rs.getString("descrizione") + ", "%>
<%= rs.getBigDecimal("latitudine") + ", "%>
<%= rs.getBigDecimal("longitudine") + "<br>"%>

<%
    }
    rs.close();
    statement.close();
    connection.close();
%>
</body>
</html>