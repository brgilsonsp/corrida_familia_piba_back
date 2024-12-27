-- Criação do Banco de Dados
CREATE DATABASE race_event_management_dev;

-- Seleção do Banco de Dados
USE race_event_management_dev;

-- Criação da Tabela de Atletas
CREATE TABLE athlete (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_uuid CHAR(36) UNIQUE NOT NULL,
    document VARCHAR(30) UNIQUE NOT NULL,
    name VARCHAR(150) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    date_of_birth DATE NOT NULL,
    modality VARCHAR(30) NOT NULL,
    bib_number INT,
    email VARCHAR(150),
    phone VARCHAR(20)
);

-- Criação da Tabela de Largadas
CREATE TABLE start_race (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_uuid CHAR(36) UNIQUE NOT NULL,
    bib_number INT NOT NULL,
    time_start TIME NOT NULL,
    monitor VARCHAR(50) NOT NULL
);

-- Criação da Tabela de Chegadas
CREATE TABLE finish_race (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_uuid CHAR(36) UNIQUE NOT NULL,
    bib_number INT NOT NULL,
    time_finish TIME NOT NULL,
    monitor VARCHAR(50) NOT NULL
);
