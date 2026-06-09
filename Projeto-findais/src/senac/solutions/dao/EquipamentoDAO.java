package senac.solutions.dao;

import senac.solutions.model.Equipamento;
import senac.solutions.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDAO {

    // ── CADASTRAR ─────────────────────────────────────────────
    public void cadastrar(Equipamento e) throws SQLException {
        String sql = "INSERT INTO equipamento (cliente_id, tipo, marca, modelo, numero_serie, descricao_defeito) VALUES (?,?,?,?,?,?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, e.getClienteId());
            ps.setString(2, e.getTipo());
            ps.setString(3, e.getMarca());
            ps.setString(4, e.getModelo());
            ps.setString(5, e.getNumeroSerie());
            ps.setString(6, e.getDescricaoDefeito());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) e.setId(keys.getInt(1));
        }
    }

    // ── BUSCAR POR ID ─────────────────────────────────────────
    public Equipamento buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM equipamento WHERE id = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        }
        return null;
    }

    // ── LISTAR POR CLIENTE ────────────────────────────────────
    public List<Equipamento> listarPorCliente(int clienteId) throws SQLException {
        String sql = "SELECT * FROM equipamento WHERE cliente_id = ?";
        List<Equipamento> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clienteId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ── LISTAR TODOS ──────────────────────────────────────────
    public List<Equipamento> listarTodos() throws SQLException {
        String sql = "SELECT * FROM equipamento ORDER BY id";
        List<Equipamento> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ── ALTERAR ───────────────────────────────────────────────
    public void alterar(Equipamento e) throws SQLException {
        String sql = "UPDATE equipamento SET tipo=?, marca=?, modelo=?, numero_serie=?, descricao_defeito=? WHERE id=?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getTipo());
            ps.setString(2, e.getMarca());
            ps.setString(3, e.getModelo());
            ps.setString(4, e.getNumeroSerie());
            ps.setString(5, e.getDescricaoDefeito());
            ps.setInt(6, e.getId());
            ps.executeUpdate();
        }
    }

    // ── EXCLUIR ───────────────────────────────────────────────
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM equipamento WHERE id=?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ── MAPEAR RESULTADO ──────────────────────────────────────
    private Equipamento mapear(ResultSet rs) throws SQLException {
        Equipamento e = new Equipamento();
        e.setId(rs.getInt("id"));
        e.setClienteId(rs.getInt("cliente_id"));
        e.setTipo(rs.getString("tipo"));
        e.setMarca(rs.getString("marca"));
        e.setModelo(rs.getString("modelo"));
        e.setNumeroSerie(rs.getString("numero_serie"));
        e.setDescricaoDefeito(rs.getString("descricao_defeito"));
        return e;
    }
}
