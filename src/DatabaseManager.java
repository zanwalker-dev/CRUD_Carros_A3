import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:veiculos.db";
    private Connection connection;

    public DatabaseManager() {
        criarTabelaVeiculos();
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
        }
        return connection;
    }

    private void criarTabelaVeiculos() {
        String sql = "CREATE TABLE IF NOT EXISTS veiculos (" +
                "placa TEXT PRIMARY KEY," +
                "tipo TEXT NOT NULL," +
                "modelo TEXT NOT NULL," +
                "marca TEXT NOT NULL," +
                "ano INTEGER NOT NULL," +
                "quilometragem INTEGER NOT NULL," +
                "preco REAL NOT NULL," +
                "is_compact BOOLEAN," +
                "espaco_porta_mala REAL," +
                "tracao TEXT," +
                "valor_atual REAL NOT NULL" +
                ");";

        try (Connection connection = getConnection();
             Statement statment = connection.createStatement()) {
            statment.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}