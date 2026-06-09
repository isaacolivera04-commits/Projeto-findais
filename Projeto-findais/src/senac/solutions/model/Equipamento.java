package senac.solutions.model;

public class Equipamento {

    private int id;
    private int clienteId;
    private String tipo;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String descricaoDefeito;

    public Equipamento() {}

    public Equipamento(int clienteId, String tipo, String marca, String modelo,
                       String numeroSerie, String descricaoDefeito) {
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.descricaoDefeito = descricaoDefeito;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public String getDescricaoDefeito() { return descricaoDefeito; }
    public void setDescricaoDefeito(String descricaoDefeito) { this.descricaoDefeito = descricaoDefeito; }

    @Override
    public String toString() {
        return String.format("Equipamento[id=%d, tipo=%s, marca=%s, modelo=%s, serie=%s]",
                id, tipo, marca, modelo, numeroSerie);
    }
}
