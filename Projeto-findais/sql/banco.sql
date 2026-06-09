-- ============================================================
-- Sistema de Gestão de Ordens de Serviço
-- Senac Solutions — Script de criação do banco de dados
-- ============================================================

CREATE DATABASE IF NOT EXISTS senac_solutions
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE senac_solutions;

-- ------------------------------------------------------------
-- Tabela: CLIENTE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS cliente (
  id         INT            NOT NULL AUTO_INCREMENT,
  nome       VARCHAR(150)   NOT NULL,
  cpf        CHAR(14)       NOT NULL,
  telefone   VARCHAR(20)    NOT NULL,
  email      VARCHAR(150),
  CONSTRAINT pk_cliente PRIMARY KEY (id),
  CONSTRAINT uq_cpf     UNIQUE (cpf)
);

-- ------------------------------------------------------------
-- Tabela: EQUIPAMENTO
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS equipamento (
  id                INT          NOT NULL AUTO_INCREMENT,
  cliente_id        INT          NOT NULL,
  tipo              VARCHAR(80)  NOT NULL,
  marca             VARCHAR(80)  NOT NULL,
  modelo            VARCHAR(100) NOT NULL,
  numero_serie      VARCHAR(100) NOT NULL,
  descricao_defeito TEXT         NOT NULL,
  CONSTRAINT pk_equipamento   PRIMARY KEY (id),
  CONSTRAINT uq_numero_serie  UNIQUE (numero_serie),
  CONSTRAINT fk_equip_cliente FOREIGN KEY (cliente_id)
    REFERENCES cliente(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ------------------------------------------------------------
-- Tabela: ORDEM_SERVICO
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ordem_servico (
  numero              INT           NOT NULL AUTO_INCREMENT,
  cliente_id          INT           NOT NULL,
  equipamento_id      INT           NOT NULL,
  data_abertura       DATE          NOT NULL,
  data_encerramento   DATE,
  responsavel         VARCHAR(150)  NOT NULL,
  observacoes         TEXT,
  custo               DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  status              ENUM('ABERTA','EM_ANDAMENTO','CONCLUIDA','CANCELADA')
                      NOT NULL DEFAULT 'ABERTA',
  CONSTRAINT pk_os         PRIMARY KEY (numero),
  CONSTRAINT fk_os_cliente FOREIGN KEY (cliente_id)
    REFERENCES cliente(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_os_equip   FOREIGN KEY (equipamento_id)
    REFERENCES equipamento(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ------------------------------------------------------------
-- Dados de teste
-- ------------------------------------------------------------
INSERT INTO cliente (nome, cpf, telefone, email) VALUES
  ('João Silva',    '123.456.789-00', '(47) 99999-0001', 'joao@email.com'),
  ('Maria Oliveira','987.654.321-00', '(47) 99999-0002', 'maria@email.com');

INSERT INTO equipamento (cliente_id, tipo, marca, modelo, numero_serie, descricao_defeito) VALUES
  (1, 'Notebook', 'Dell', 'Inspiron 15', 'SN-001-DELL', 'Não liga'),
  (2, 'Desktop',  'HP',   'Compaq 8200', 'SN-002-HP',   'Tela azul ao iniciar');

INSERT INTO ordem_servico (cliente_id, equipamento_id, data_abertura, responsavel, status) VALUES
  (1, 1, CURDATE(), 'Carlos Técnico', 'ABERTA'),
  (2, 2, CURDATE(), 'Ana Técnica',    'EM_ANDAMENTO');
