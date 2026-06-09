package senac.solutions.dao;

import senac.solutions.model.Cliente;
import senac.solutions.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // ── CADASTRAR ─────────────────────────────────────────────
    public void cadastrar(Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getTelefone());
            ps.setString(4, c.getEmail());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) c.setId(keys.getInt(1));
        }
    }

    // ── BUSCAR POR ID ─────────────────────────────────────────
    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        }
        return null;
    }

    // ── BUSCAR POR CPF ────────────────────────────────────────
    public Cliente buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        }
        return null;
    }

    // ── BUSCAR POR NOME ───────────────────────────────────────
    public List<Cliente> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE nome LIKE ?";
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ── LISTAR TODOS ──────────────────────────────────────────
    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM cliente ORDER BY nome";
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ── ALTERAR ───────────────────────────────────────────────
    public void alterar(Cliente c) throws SQLException {
        String sql = "UPDATE cliente SET nome=?, cpf=?, telefone=?, email=? WHERE id=?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getTelefone());
            ps.setString(4, c.getEmail());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    // ── EXCLUIR ───────────────────────────────────────────────
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id=?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ── MAPEAR RESULTADO ──────────────────────────────────────
    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setTelefone(rs.getString("telefone"));
        c.setEmail(rs.getString("email"));
        return c;
    }
}
