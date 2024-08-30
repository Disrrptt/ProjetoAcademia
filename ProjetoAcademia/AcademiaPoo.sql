create database academia;
use academia;

-- Criação da tabela de Alunos
CREATE TABLE aluno (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    genero VARCHAR(10),
    telefone VARCHAR(15),
    email VARCHAR(100),
    endereco VARCHAR(255)
);

-- Criação da tabela de Treinos
CREATE TABLE treino (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(50) NOT NULL,
    duracao_minutos INT,
    nivel_dificuldade VARCHAR(20),
    descricao TEXT,
    objetivo VARCHAR(50),
    equipamentos_necessarios VARCHAR(255),
    dias_semana VARCHAR(50)
);

-- Criação da tabela de Associação entre Aluno e Treino
CREATE TABLE aluno_Treino (
    id INT PRIMARY KEY AUTO_INCREMENT,
    aluno_id INT,
    treino_id INT,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id),
    FOREIGN KEY (treino_id) REFERENCES treino(id)
);
