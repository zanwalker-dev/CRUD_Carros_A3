import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorVeiculos {
    private DatabaseManager dbManager;

    public GerenciadorVeiculos() {
        this.dbManager = new DatabaseManager();
    }

    public void adicionar(Carro carro) {
        if (placaExistente(carro.getPlaca())) {
            throw new IllegalArgumentException("Placa já cadastrada: " + carro.getPlaca());
        }

        String sql = "INSERT INTO veiculos(placa, tipo, modelo, marca, ano, quilometragem, preco, " +
                "is_compact, espaco_porta_mala, tracao, valor_atual) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, carro.getPlaca());
            pstmt.setString(2, getTipoVeiculo(carro));
            pstmt.setString(3, carro.getModelo());
            pstmt.setString(4, carro.getMarca());
            pstmt.setInt(5, carro.getAno());
            pstmt.setInt(6, carro.getQuilometragem());

            if (carro instanceof Hatch) {
                Hatch hatch = (Hatch) carro;
                pstmt.setDouble(7, hatch.getPreco());
                pstmt.setBoolean(8, hatch.isCompact());
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.VARCHAR);
            } else if (carro instanceof Sedan) {
                Sedan sedan = (Sedan) carro;
                pstmt.setDouble(7, sedan.getPreco());
                pstmt.setNull(8, Types.BOOLEAN);
                pstmt.setDouble(9, sedan.getEspacoPortaMala());
                pstmt.setNull(10, Types.VARCHAR);
            } else if (carro instanceof SUV) {
                SUV suv = (SUV) carro;
                pstmt.setDouble(7, suv.getPreco());
                pstmt.setNull(8, Types.BOOLEAN);
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setString(10, suv.getTracao());
            }

            pstmt.setDouble(11, carro.valorAtual());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar veículo: " + e.getMessage());
            throw new RuntimeException("Erro no banco de dados", e);
        }
    }

    private String getTipoVeiculo(Carro carro) {
        if (carro instanceof Hatch) return "HATCH";
        if (carro instanceof Sedan) return "SEDAN";
        if (carro instanceof SUV) return "SUV";
        return "GENERICO";
    }

    public boolean remover(String placa) {
        String sql = "DELETE FROM veiculos WHERE placa = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao remover veículo: " + e.getMessage());
            throw new RuntimeException("Erro no banco de dados", e);
        }
    }

    public Carro buscarPorPlaca(String placa) {
        String sql = "SELECT * FROM veiculos WHERE placa = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return criarCarroFromResultSet(rs);
            }
            return null;

        } catch (SQLException e) {
            System.err.println("Erro ao buscar veículo: " + e.getMessage());
            throw new RuntimeException("Erro no banco de dados", e);
        }
    }

    private Carro criarCarroFromResultSet(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String placa = rs.getString("placa");
        String modelo = rs.getString("modelo");
        String marca = rs.getString("marca");
        int ano = rs.getInt("ano");
        int quilometragem = rs.getInt("quilometragem");
        double preco = rs.getDouble("preco");

        switch (tipo) {
            case "HATCH":
                boolean isCompact = rs.getBoolean("is_compact");
                return new Hatch(placa, modelo, marca, ano, quilometragem, preco, isCompact);
            case "SEDAN":
                double espacoPortaMala = rs.getDouble("espaco_porta_mala");
                return new Sedan(placa, modelo, marca, ano, quilometragem, preco, espacoPortaMala);
            case "SUV":
                String tracao = rs.getString("tracao");
                return new SUV(placa, modelo, marca, ano, quilometragem, preco, tracao);
            default:
                throw new IllegalArgumentException("Tipo de veículo desconhecido: " + tipo);
        }
    }

    public List<Carro> buscarPorMarca(String marca) {
        String sql = "SELECT * FROM veiculos WHERE marca = ?";
        return buscarComQuery(sql, marca);
    }

    public List<Carro> buscarPorModelo(String modelo) {
        String sql = "SELECT * FROM veiculos WHERE modelo = ?";
        return buscarComQuery(sql, modelo);
    }

    private List<Carro> buscarComQuery(String sql, String parametro) {
        List<Carro> carros = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, parametro);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                carros.add(criarCarroFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar veículos: " + e.getMessage());
            throw new RuntimeException("Erro no banco de dados", e);
        }

        return carros;
    }

    public List<Hatch> listarHatchs() {
        return listarPorTipo("HATCH").stream()
                .map(c -> (Hatch) c)
                .collect(Collectors.toList());
    }

    public List<Sedan> listarSedans() {
        return listarPorTipo("SEDAN").stream()
                .map(c -> (Sedan) c)
                .collect(Collectors.toList());
    }

    public List<SUV> listarSUVs() {
        return listarPorTipo("SUV").stream()
                .map(c -> (SUV) c)
                .collect(Collectors.toList());
    }

    private List<Carro> listarPorTipo(String tipo) {
        String sql = "SELECT * FROM veiculos WHERE tipo = ?";
        return buscarComQuery(sql, tipo);
    }

    public boolean placaExistente(String placa) {
        String sql = "SELECT 1 FROM veiculos WHERE placa = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.err.println("Erro ao verificar placa: " + e.getMessage());
            throw new RuntimeException("Erro no banco de dados", e);
        }
    }

    public List<Carro> listarTodos() {
        String sql = "SELECT * FROM veiculos";
        List<Carro> carros = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                carros.add(criarCarroFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar veículos: " + e.getMessage());
            throw new RuntimeException("Erro no banco de dados", e);
        }

        return carros;
    }

    public void atualizarCarro(Carro carro) {
        String sql = "UPDATE veiculos SET modelo = ?, marca = ?, ano = ?, quilometragem = ?, preco = ?, " +
                "is_compact = ?, espaco_porta_mala = ?, tracao = ?, valor_atual = ? WHERE placa = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, carro.getModelo());
            pstmt.setString(2, carro.getMarca());
            pstmt.setInt(3, carro.getAno());
            pstmt.setInt(4, carro.getQuilometragem());

            if (carro instanceof Hatch) {
                Hatch hatch = (Hatch) carro;
                pstmt.setDouble(5, hatch.getPreco());
                pstmt.setBoolean(6, hatch.isCompact());
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setNull(8, Types.VARCHAR);
            } else if (carro instanceof Sedan) {
                Sedan sedan = (Sedan) carro;
                pstmt.setDouble(5, sedan.getPreco());
                pstmt.setNull(6, Types.BOOLEAN);
                pstmt.setDouble(7, sedan.getEspacoPortaMala());
                pstmt.setNull(8, Types.VARCHAR);
            } else if (carro instanceof SUV) {
                SUV suv = (SUV) carro;
                pstmt.setDouble(5, suv.getPreco());
                pstmt.setNull(6, Types.BOOLEAN);
                pstmt.setNull(7, Types.DOUBLE);
                pstmt.setString(8, suv.getTracao());
            }

            pstmt.setDouble(9, carro.valorAtual());
            pstmt.setString(10, carro.getPlaca());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar veículo: " + e.getMessage());
            throw new RuntimeException("Erro no banco de dados", e);
        }
    }
}