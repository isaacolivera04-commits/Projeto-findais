package senac.solutions;

import senac.solutions.dao.ClienteDAO;
import senac.solutions.dao.EquipamentoDAO;
import senac.solutions.dao.OrdemServicoDAO;
import senac.solutions.model.Cliente;
import senac.solutions.model.Equipamento;
import senac.solutions.model.OrdemServico;
import senac.solutions.model.StatusOS;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static ClienteDAO clienteDAO = new ClienteDAO();
    static EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    static OrdemServicoDAO osDAO = new OrdemServicoDAO();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Escolha: ");
            try {
                switch (opcao) {
                    case 1 -> menuClientes();
                    case 2 -> menuEquipamentos();
                    case 3 -> menuOS();
                    case 4 -> menuRelatorios();
                    case 0 -> System.out.println("Encerrando sistema...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (SQLException e) {
                System.out.println("Erro no banco de dados: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    // ── MENUS ──────────────────────────────────────────────────
    static void exibirMenu() {
        System.out.println("\n=============================");
        System.out.println("   SENAC SOLUTIONS — MENU");
        System.out.println("=============================");
        System.out.println("1. Clientes");
        System.out.println("2. Equipamentos");
        System.out.println("3. Ordens de Serviço");
        System.out.println("4. Relatórios");
        System.out.println("0. Sair");
    }

    static void menuClientes() throws SQLException {
        System.out.println("\n--- CLIENTES ---");
        System.out.println("1. Cadastrar  2. Buscar  3. Listar  4. Alterar  5. Excluir  0. Voltar");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> cadastrarCliente();
            case 2 -> buscarCliente();
            case 3 -> listarClientes();
            case 4 -> alterarCliente();
            case 5 -> excluirCliente();
        }
    }

    static void menuEquipamentos() throws SQLException {
        System.out.println("\n--- EQUIPAMENTOS ---");
        System.out.println("1. Cadastrar  2. Buscar  3. Listar  4. Alterar  5. Excluir  0. Voltar");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> cadastrarEquipamento();
            case 2 -> buscarEquipamento();
            case 3 -> listarEquipamentos();
            case 4 -> alterarEquipamento();
            case 5 -> excluirEquipamento();
        }
    }

    static void menuOS() throws SQLException {
        System.out.println("\n--- ORDENS DE SERVIÇO ---");
        System.out.println("1. Abrir OS  2. Atualizar Status  3. Consultar  4. Encerrar  0. Voltar");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> abrirOS();
            case 2 -> atualizarStatusOS();
            case 3 -> consultarOS();
            case 4 -> encerrarOS();
        }
    }

    static void menuRelatorios() throws SQLException {
        System.out.println("\n--- RELATÓRIOS ---");
        System.out.println("1. OS em andamento  2. OS finalizadas  3. Clientes  4. Equipamentos");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> { System.out.println("\n-- OS EM ANDAMENTO --"); osDAO.listarEmAndamento().forEach(System.out::println); }
            case 2 -> { System.out.println("\n-- OS FINALIZADAS --"); osDAO.listarFinalizadas().forEach(System.out::println); }
            case 3 -> { System.out.println("\n-- CLIENTES --"); clienteDAO.listarTodos().forEach(System.out::println); }
            case 4 -> { System.out.println("\n-- EQUIPAMENTOS --"); equipamentoDAO.listarTodos().forEach(System.out::println); }
        }
    }

    // ── CLIENTES ───────────────────────────────────────────────
    static void cadastrarCliente() throws SQLException {
        System.out.println("\n-- Cadastrar Cliente --");
        String nome = lerTexto("Nome: ");
        String cpf  = lerTexto("CPF (XXX.XXX.XXX-XX): ");
        String tel  = lerTexto("Telefone: ");
        String email = lerTexto("E-mail (Enter para pular): ");
        Cliente c = new Cliente(nome, cpf, tel, email.isBlank() ? null : email);
        clienteDAO.cadastrar(c);
        System.out.println("Cliente cadastrado com ID: " + c.getId());
    }

    static void buscarCliente() throws SQLException {
        String cpf = lerTexto("CPF do cliente: ");
        Cliente c = clienteDAO.buscarPorCpf(cpf);
        System.out.println(c != null ? c : "Cliente não encontrado.");
    }

    static void listarClientes() throws SQLException {
        List<Cliente> lista = clienteDAO.listarTodos();
        if (lista.isEmpty()) System.out.println("Nenhum cliente cadastrado.");
        else lista.forEach(System.out::println);
    }

    static void alterarCliente() throws SQLException {
        int id = lerInt("ID do cliente: ");
        Cliente c = clienteDAO.buscarPorId(id);
        if (c == null) { System.out.println("Cliente não encontrado."); return; }
        c.setNome(lerTexto("Novo nome (" + c.getNome() + "): "));
        c.setTelefone(lerTexto("Novo telefone (" + c.getTelefone() + "): "));
        clienteDAO.alterar(c);
        System.out.println("Cliente alterado com sucesso.");
    }

    static void excluirCliente() throws SQLException {
        int id = lerInt("ID do cliente a excluir: ");
        clienteDAO.excluir(id);
        System.out.println("Cliente excluído.");
    }

    // ── EQUIPAMENTOS ───────────────────────────────────────────
    static void cadastrarEquipamento() throws SQLException {
        System.out.println("\n-- Cadastrar Equipamento --");
        int clienteId = lerInt("ID do cliente: ");
        String tipo   = lerTexto("Tipo (notebook/desktop...): ");
        String marca  = lerTexto("Marca: ");
        String modelo = lerTexto("Modelo: ");
        String serie  = lerTexto("Número de série: ");
        String defeito = lerTexto("Descrição do defeito: ");
        Equipamento e = new Equipamento(clienteId, tipo, marca, modelo, serie, defeito);
        equipamentoDAO.cadastrar(e);
        System.out.println("Equipamento cadastrado com ID: " + e.getId());
    }

    static void buscarEquipamento() throws SQLException {
        int id = lerInt("ID do equipamento: ");
        Equipamento e = equipamentoDAO.buscarPorId(id);
        System.out.println(e != null ? e : "Equipamento não encontrado.");
    }

    static void listarEquipamentos() throws SQLException {
        equipamentoDAO.listarTodos().forEach(System.out::println);
    }

    static void alterarEquipamento() throws SQLException {
        int id = lerInt("ID do equipamento: ");
        Equipamento e = equipamentoDAO.buscarPorId(id);
        if (e == null) { System.out.println("Equipamento não encontrado."); return; }
        e.setDescricaoDefeito(lerTexto("Nova descrição do defeito: "));
        equipamentoDAO.alterar(e);
        System.out.println("Equipamento alterado.");
    }

    static void excluirEquipamento() throws SQLException {
        int id = lerInt("ID do equipamento: ");
        equipamentoDAO.excluir(id);
        System.out.println("Equipamento excluído.");
    }

    // ── ORDENS DE SERVIÇO ──────────────────────────────────────
    static void abrirOS() throws SQLException {
        System.out.println("\n-- Abrir Ordem de Serviço --");
        int clienteId     = lerInt("ID do cliente: ");
        int equipamentoId = lerInt("ID do equipamento: ");
        String responsavel = lerTexto("Responsável técnico: ");
        String obs         = lerTexto("Observações: ");
        OrdemServico os = new OrdemServico(clienteId, equipamentoId, responsavel, obs);
        osDAO.abrir(os);
        System.out.println("OS aberta com número: " + os.getNumero());
    }

    static void atualizarStatusOS() throws SQLException {
        int numero = lerInt("Número da OS: ");
        System.out.println("1. EM_ANDAMENTO  2. CONCLUIDA  3. CANCELADA");
        int op = lerInt("Novo status: ");
        StatusOS novo = switch (op) {
            case 1 -> StatusOS.EM_ANDAMENTO;
            case 2 -> StatusOS.CONCLUIDA;
            case 3 -> StatusOS.CANCELADA;
            default -> null;
        };
        if (novo != null) { osDAO.atualizarStatus(numero, novo); System.out.println("Status atualizado."); }
    }

    static void consultarOS() throws SQLException {
        int numero = lerInt("Número da OS: ");
        OrdemServico os = osDAO.buscarPorNumero(numero);
        System.out.println(os != null ? os : "OS não encontrada.");
    }

    static void encerrarOS() throws SQLException {
        int numero = lerInt("Número da OS: ");
        double custo = lerDouble("Custo final (R$): ");
        osDAO.encerrar(numero, custo);
        System.out.println("OS encerrada com sucesso.");
    }

    // ── HELPERS DE LEITURA ─────────────────────────────────────
    static String lerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    static int lerInt(String prompt) {
        System.out.print(prompt);
        try { int v = Integer.parseInt(scanner.nextLine().trim()); return v; }
        catch (NumberFormatException e) { return -1; }
    }

    static double lerDouble(String prompt) {
        System.out.print(prompt);
        try { return Double.parseDouble(scanner.nextLine().trim().replace(",", ".")); }
        catch (NumberFormatException e) { return 0.0; }
    }
}
