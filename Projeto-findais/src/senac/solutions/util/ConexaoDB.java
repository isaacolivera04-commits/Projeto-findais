package senac.solutions.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    // ⚠️ Altere as configurações abaixo conforme seu ambiente
    private static final String URL      = "jdbc:mysql://localhost:3306/senac_solutions";
    private static final String USUARIO  = "root";
    private static final String SENHA    = ""; // coloque sua senha aqui

    private ConexaoDB() {}

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public static void fechar(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
