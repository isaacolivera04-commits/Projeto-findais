package senac.solutions.dao;

import senac.solutions.model.OrdemServico;
import senac.solutions.model.StatusOS;
import senac.solutions.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {

    // ── ABRIR OS ──────────────────────────────────────────────
    public void abrir(OrdemServico os) throws SQLException {
        String sql = "INSERT INTO ordem_servico (cliente_id, equipamento_id, data_abertura, responsavel, observacoes, custo, status) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, os.getClienteId());
            ps.setInt(2, os.getEquipamentoId());
            ps.setDate(3, Date.valueOf(os.getDataAbertura()));
            ps.setString(4, os.getResponsavel());
            ps.setString(5, os.getObservacoes());
            ps.setDouble(6, os.getCusto());
            ps.setString(7, os.getStatus().name());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) os.setNumero(keys.getInt(1));
        }
    }

    // ── ATUALIZAR STATUS ──────────────────────────────────────
    public void atualizarStatus(int numero, StatusOS novoStatus) throws SQLException {
        String sql = "UPDATE ordem_servico SET status=? WHERE numero=?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoStatus.name());
            ps.setInt(2, numero);
            ps.executeUpdate();
        }
    }

    // ── ENCERRAR OS ───────────────────────────────────────────
    public void encerrar(int numero, double custo) throws SQLException {
        String sql = "UPDATE ordem_servico SET status='CONCLUIDA', data_encerramento=CURDATE(), custo=? WHERE numero=?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, custo);
            ps.setInt(2, numero);
            ps.executeUpdate();
        }
    }

    // ── BUSCAR POR NÚMERO ─────────────────────────────────────
    public OrdemServico buscarPorNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM ordem_servico WHERE numero = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        }
        return null;
    }

    // ── LISTAR EM ANDAMENTO ───────────────────────────────────
    public List<OrdemServico> listarEmAndamento() throws SQLException {
        return listarPorStatus(StatusOS.ABERTA, StatusOS.EM_ANDAMENTO);
    }

    // ── LISTAR FINALIZADAS ────────────────────────────────────
    public List<OrdemServico> listarFinalizadas() throws SQLException {
        return listarPorStatus(StatusOS.CONCLUIDA, StatusOS.CANCELADA);
    }

    // ── LISTAR POR CLIENTE ────────────────────────────────────
    public List<OrdemServico> listarPorCliente(int clienteId) throws SQLException {
        String sql = "SELECT * FROM ordem_servico WHERE cliente_id = ? ORDER BY data_abertura DESC";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clienteId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ── HELPER: LISTAR POR STATUS ─────────────────────────────
    private List<OrdemServico> listarPorStatus(StatusOS... statuses) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM ordem_servico WHERE status IN (");
        for (int i = 0; i < statuses.length; i++) sql.append(i == 0 ? "?" : ",?");
        sql.append(") ORDER BY data_abertura DESC");

        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < statuses.length; i++) ps.setString(i + 1, statuses[i].name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ── MAPEAR RESULTADO ──────────────────────────────────────
    private OrdemServico mapear(ResultSet rs) throws SQLException {
        OrdemServico os = new OrdemServico();
        os.setNumero(rs.getInt("numero"));
        os.setClienteId(rs.getInt("cliente_id"));
        os.setEquipamentoId(rs.getInt("equipamento_id"));
        os.setDataAbertura(rs.getDate("data_abertura").toLocalDate());
        Date enc = rs.getDate("data_encerramento");
        if (enc != null) os.setDataEncerramento(enc.toLocalDate());
        os.setResponsavel(rs.getString("responsavel"));
        os.setObservacoes(rs.getString("observacoes"));
        os.setCusto(rs.getDouble("custo"));
        os.setStatus(StatusOS.valueOf(rs.getString("status")));
        return os;
    }
}
