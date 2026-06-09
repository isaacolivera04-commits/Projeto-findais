package senac.solutions.model;

import java.time.LocalDate;

public class OrdemServico {

    private int numero;
    private int clienteId;
    private int equipamentoId;
    private LocalDate dataAbertura;
    private LocalDate dataEncerramento;
    private String responsavel;
    private String observacoes;
    private double custo;
    private StatusOS status;

    public OrdemServico() {}

    public OrdemServico(int clienteId, int equipamentoId, String responsavel, String observacoes) {
        this.clienteId = clienteId;
        this.equipamentoId = equipamentoId;
        this.responsavel = responsavel;
        this.observacoes = observacoes;
        this.dataAbertura = LocalDate.now();
        this.status = StatusOS.ABERTA;
        this.custo = 0.0;
    }

    // Getters e Setters
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public int getEquipamentoId() { return equipamentoId; }
    public void setEquipamentoId(int equipamentoId) { this.equipamentoId = equipamentoId; }

    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDate getDataEncerramento() { return dataEncerramento; }
    public void setDataEncerramento(LocalDate dataEncerramento) { this.dataEncerramento = dataEncerramento; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public double getCusto() { return custo; }
    public void setCusto(double custo) { this.custo = custo; }

    public StatusOS getStatus() { return status; }
    public void setStatus(StatusOS status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("OrdemServico[numero=%d, status=%s, responsavel=%s, custo=%.2f]",
                numero, status, responsavel, custo);
    }
}
