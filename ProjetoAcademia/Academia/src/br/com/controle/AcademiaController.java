package br.com.controle;

import br.com.modelo.Aluno;
import br.com.modelo.Treino;
import br.com.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcademiaController {

    private Connection conexao; 

    // Método para listar alunos com treinos (operação JOIN)
    public List<Aluno> listarAlunosComTreinos() {
        List<Aluno> listaAlunos = new ArrayList<>();
        String sql = "SELECT a.id AS aluno_id, a.nome AS aluno_nome, "
                + "t.id AS treino_id, t.tipo AS treino_tipo "
                + "FROM aluno a "
                + "LEFT JOIN aluno_treino at ON a.id = at.aluno_id "
                + "LEFT JOIN treino t ON at.treino_id = t.id";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int alunoId = rs.getInt("aluno_id");
                String alunoNome = rs.getString("aluno_nome");

                Aluno aluno = new Aluno();
                aluno.setId(alunoId);
                aluno.setNome(alunoNome);

                int treinoId = rs.getInt("treino_id");
                String treinoTipo = rs.getString("treino_tipo");

                Treino treino = new Treino();
                treino.setId(treinoId);
                treino.setTipo(treinoTipo);

                aluno.getTreinos().add(treino);

                listaAlunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }

        return listaAlunos;
    }

    // Método para obter informações detalhadas de um aluno com base no ID
    public Aluno obterAluno(int idAluno) throws SQLException {
        Aluno aluno = null;
        String sql = "SELECT * FROM aluno WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");

                    aluno = new Aluno();
                    aluno.setId(id);
                    aluno.setNome(nome);
                }
            }
        }

        return aluno;
    }

    // Método para obter informações detalhadas de um treino com base no ID
    public Treino obterTreino(int idTreino) throws SQLException {
        Treino treino = null;
        String sql = "SELECT * FROM treino WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idTreino);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String tipo = rs.getString("tipo");

                    treino = new Treino();
                    treino.setId(id);
                    treino.setTipo(tipo);
                }
            }
        }

        return treino;
    }

    // Método para associar treino a aluno e obter informações detalhadas
    public Map<String, String> associarTreinoAoAluno(int idAluno, int idTreino) throws SQLException {
        String sql = "INSERT INTO aluno_treino (aluno_id, treino_id) VALUES (?, ?)";
        Map<String, String> resultado = new HashMap<>();

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idAluno);
            stmt.setInt(2, idTreino);

            stmt.executeUpdate();

            // Obtém o ID gerado para aluno_treino (se necessário)
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int alunoTreinoId = generatedKeys.getInt(1);

                    // Obtem as informações do aluno e do treino usando seus métodos existentes
                    Aluno aluno = obterAluno(idAluno);
                    Treino treino = obterTreino(idTreino);

                    resultado.put("alunoNome", aluno.getNome());
                    resultado.put("treinoTipo", treino.getTipo());
                }
            }
        }

        return resultado;
    }

    public List<Treino> listarTreinosDoAluno(int idAluno) throws SQLException {
        List<Treino> treinos = new ArrayList<>();
        String sql = "SELECT t.id, t.tipo FROM treino t "
                + "INNER JOIN aluno_treino at ON t.id = at.treino_id "
                + "WHERE at.aluno_id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idTreino = rs.getInt("id");
                    String tipoTreino = rs.getString("tipo");

                    Treino treino = new Treino();
                    treino.setId(idTreino);
                    treino.setTipo(tipoTreino);

                    treinos.add(treino);
                }
            }
        }

        return treinos;
    }
}
