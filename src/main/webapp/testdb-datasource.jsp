<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%
    Context ctx = null;
    DataSource ds = null;
    Connection connessione = null;
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
    try {
        ctx = new InitialContext();
        ds = (DataSource) ctx.lookup("java:comp/env/jdbc/caf");
        connessione = ds.getConnection();

        if (connessione != null) {
            statement = connessione.createStatement();
            rs = statement.executeQuery("SELECT * FROM impianto");

            while (rs.next()) {
%>
<%= rs.getString("idimpianto") + ") "%>
<%= rs.getString("descrizione") + ", "%>
<%= rs.getBigDecimal("latitudine") + ", "%>
<%= rs.getBigDecimal("longitudine") + "<br>"%>
<%
            }
        } else {
            out.println("Impossibile stabilire una connessione al database.");
        }
    } catch (Exception e) {
        out.println("Si è verificato un errore: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (connessione != null) connessione.close();
        } catch (SQLException e) {
            out.println("Si è verificato un errore durante la chiusura delle risorse: " + e.getMessage());
        }
    }
%>
</body>
</html>
