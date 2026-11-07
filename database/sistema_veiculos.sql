-- =====================================================================
-- Sistema Automotivo - Gestao de Estoque de Veiculos
-- Modelo fisico (MySQL 8)
-- ATENCAO: este script APAGA e recria o banco sistema_veiculos.
-- =====================================================================

DROP DATABASE IF EXISTS sistema_veiculos;
CREATE DATABASE sistema_veiculos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sistema_veiculos;

-- ---------------------------------------------------------------------
-- MARCA
-- ---------------------------------------------------------------------
CREATE TABLE marca (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    nome          VARCHAR(60)  NOT NULL,
    pais_origem   VARCHAR(60),
    data_cadastro DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_marca_nome UNIQUE (nome)
);

-- ---------------------------------------------------------------------
-- MODELO  (N modelos -> 1 marca)
-- ---------------------------------------------------------------------
CREATE TABLE modelo (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    nome          VARCHAR(60)  NOT NULL,
    tipo          VARCHAR(20)  NOT NULL,   -- CARRO, MOTO, CAMINHONETE, SUV, CAMINHAO
    marca_id      BIGINT       NOT NULL,
    data_cadastro DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_modelo_marca FOREIGN KEY (marca_id) REFERENCES marca (id)
);

-- ---------------------------------------------------------------------
-- VEICULO  (N veiculos -> 1 modelo)
-- ---------------------------------------------------------------------
CREATE TABLE veiculo (
    id            BIGINT         NOT NULL AUTO_INCREMENT,
    modelo_id     BIGINT         NOT NULL,
    ano           INT            NOT NULL,
    cor           VARCHAR(30)    NOT NULL,
    preco         DECIMAL(12, 2) NOT NULL,
    quilometragem INT            NOT NULL,
    status        VARCHAR(20)    NOT NULL, -- DISPONIVEL, RESERVADO, VENDIDO, MANUTENCAO
    data_cadastro DATETIME(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_veiculo_modelo FOREIGN KEY (modelo_id) REFERENCES modelo (id)
);

-- Indices para deixar os filtros mais rapidos (requisito de performance)
CREATE INDEX idx_veiculo_status ON veiculo (status);
CREATE INDEX idx_veiculo_preco  ON veiculo (preco);
CREATE INDEX idx_veiculo_ano    ON veiculo (ano);

-- ---------------------------------------------------------------------
-- DADOS DE EXEMPLO
-- ---------------------------------------------------------------------
INSERT INTO marca (nome, pais_origem, data_cadastro) VALUES
    ('Toyota',     'Japao',    NOW()),
    ('Honda',      'Japao',    NOW()),
    ('Volkswagen', 'Alemanha', NOW()),
    ('Fiat',       'Italia',   NOW()),
    ('Chevrolet',  'EUA',      NOW());

INSERT INTO modelo (nome, tipo, marca_id, data_cadastro) VALUES
    ('Corolla',  'CARRO',       1, NOW()),
    ('Hilux',    'CAMINHONETE', 1, NOW()),
    ('Civic',    'CARRO',       2, NOW()),
    ('CG 160',   'MOTO',        2, NOW()),
    ('T-Cross',  'SUV',         3, NOW()),
    ('Polo',     'CARRO',       3, NOW()),
    ('Strada',   'CAMINHONETE', 4, NOW()),
    ('Argo',     'CARRO',       4, NOW()),
    ('Onix',     'CARRO',       5, NOW()),
    ('Tracker',  'SUV',         5, NOW());

INSERT INTO veiculo (modelo_id, ano, cor, preco, quilometragem, status, data_cadastro) VALUES
    (1,  2022, 'Prata',    138900.00, 25000, 'DISPONIVEL', NOW()),
    (2,  2021, 'Branco',   219000.00, 60000, 'DISPONIVEL', NOW()),
    (3,  2020, 'Preto',    119500.00, 48000, 'RESERVADO',  NOW()),
    (4,  2023, 'Vermelho',  16900.00,  5000, 'DISPONIVEL', NOW()),
    (5,  2024, 'Cinza',    145000.00,  8000, 'DISPONIVEL', NOW()),
    (6,  2019, 'Azul',      69900.00, 72000, 'VENDIDO',    NOW()),
    (7,  2023, 'Branco',   112000.00, 15000, 'DISPONIVEL', NOW()),
    (8,  2022, 'Vermelho',  74500.00, 31000, 'MANUTENCAO', NOW()),
    (9,  2021, 'Prata',     72900.00, 40000, 'DISPONIVEL', NOW()),
    (10, 2023, 'Preto',    134900.00, 12000, 'DISPONIVEL', NOW());

-- Consultas de exemplo
-- SELECT * FROM veiculo WHERE status = 'DISPONIVEL';
-- SELECT v.id, ma.nome AS marca, mo.nome AS modelo, v.ano, v.preco, v.status
--   FROM veiculo v JOIN modelo mo ON mo.id = v.modelo_id JOIN marca ma ON ma.id = mo.marca_id
--  WHERE v.preco BETWEEN 70000 AND 140000 ORDER BY v.preco;
