-- Criação do Banco de Dados
CREATE DATABASE race_event_management;

-- Seleção do Banco de Dados
USE race_event_management;

-- Criação da Tabela de Atletas
CREATE TABLE athlete (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_uuid CHAR(36) UNIQUE NOT NULL,
    document VARCHAR(30) UNIQUE NOT NULL,
    athlete_name VARCHAR(150) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    date_of_birth DATE NOT NULL,
    modality VARCHAR(30) NOT NULL,
    bib_number INT,
    monitor_name VARCHAR(150)
);

-- Criação da Tabela de Largada Geraç
CREATE TABLE start_race_general (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_uuid CHAR(36) UNIQUE NOT NULL,
    time_start TIME(3) NOT NULL,
    monitor_name VARCHAR(50) NOT NULL
);

-- Criação da Tabela de Largadas
CREATE TABLE start_race (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_uuid CHAR(36) UNIQUE NOT NULL,
    bib_number INT NOT NULL,
    time_start TIME(3) NOT NULL,
    monitor_name VARCHAR(50) NOT NULL
);

-- Criação da Tabela de Chegadas
CREATE TABLE finish_race (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_uuid CHAR(36) UNIQUE NOT NULL,
    bib_number INT NOT NULL,
    time_finish TIME(3) NOT NULL,
    monitor_name VARCHAR(50) NOT NULL
);

-- Criação da Tabela de Status de finalização da corrida
CREATE TABLE status_finish_general_race (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    time_finish_general TIME(3) NOT NULL,
    status_general_race VARCHAR(20) NOT NULL,
    monitor_name VARCHAR(50) NOT NULL
);

-- Criação da Tabela de Classificação da corrida
CREATE TABLE classification (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    bib_number INT NOT NULL,
    athlete_name VARCHAR(150) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    modality VARCHAR(30) NOT NULL,
    total_time TIME(3) NOT NULL,
    monitor_name_finish_race VARCHAR(50) NOT NULL
);
