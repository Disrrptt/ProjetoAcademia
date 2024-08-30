package br.com.controle;

import br.com.modelo.Treino;
import br.com.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class TreinoController {

    // Método para cadastrar um novo treino
    public void cadastrarTreino(Treino treino, Set<String> diasSemana) {
        String sql = "INSERT INTO treino (tipo, duracao_minutos, nivel_dificuldade, descricao, objetivo, equipamentos_necessarios, dias_semana) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, treino.getTipo());
            stmt.setString(2, treino.getDuracao());
            stmt.setString(3, treino.getNivelDificuldade());
            stmt.setString(4, treino.getDescricao());
            stmt.setString(5, treino.getObjetivo());
            stmt.setString(6, treino.getEquipamentos());
            stmt.setString(7, String.join(",", diasSemana));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    // Método para obter a lista de todos os treinos
    public List<Treino> listarTreinos() {
        List<Treino> listaTreinos = new ArrayList<>();
        String sql = "SELECT * FROM treino";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Treino treino = new Treino();
                treino.setId(rs.getInt("id"));
                treino.setTipo(rs.getString("tipo"));
                treino.setDuracao(rs.getString("duracao_minutos"));
                treino.setNivelDificuldade(rs.getString("nivel_dificuldade"));
                treino.setDescricao(rs.getString("descricao"));
                treino.setObjetivo(rs.getString("objetivo"));
                treino.setEquipamentos(rs.getString("equipamentos_necessarios"));
                treino.setDiasSemana(rs.getString("dias_semana"));

                listaTreinos.add(treino);
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }

        return listaTreinos;
    }

    // Método para obter um treino por ID
    public Treino obterTreinoPorId(int id) {
        Treino treino = null;
        String sql = "SELECT * FROM treino WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    treino = new Treino();
                    treino.setId(rs.getInt("id"));
                    treino.setTipo(rs.getString("tipo"));
                    treino.setDuracao(rs.getString("duracao_minutos"));
                    treino.setNivelDificuldade(rs.getString("nivel_dificuldade"));
                    treino.setDescricao(rs.getString("descricao"));
                    treino.setObjetivo(rs.getString("objetivo"));
                    treino.setEquipamentos(rs.getString("equipamentos_necessarios"));
                    treino.setDiasSemana(rs.getString("dias_semana"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }

        return treino;
    }

    // Método para atualizar um treino
    public void atualizarTreino(Treino treino) {
        String sql = "UPDATE treino SET tipo = ?, duracao_minutos = ?, nivel_dificuldade = ?, descricao = ?, objetivo = ?, equipamentos_necessarios = ?, dias_semana = ? WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, treino.getTipo());
            stmt.setString(2, treino.getDuracao());
            stmt.setString(3, treino.getNivelDificuldade());
            stmt.setString(4, treino.getDescricao());
            stmt.setString(5, treino.getObjetivo());
            stmt.setString(6, treino.getEquipamentos());
            stmt.setString(7, treino.getDiasSemana());
            stmt.setInt(8, treino.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }

    // Método para excluir um treino por ID
    public void excluirTreino(int id) {
        String sqlDeleteTreino = "DELETE FROM treino WHERE id = ?";
        String sqlDeleteAlunoTreino = "DELETE FROM aluno_treino WHERE treino_id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmtDeleteTreino = conexao.prepareStatement(sqlDeleteTreino);
                PreparedStatement stmtDeleteAlunoTreino = conexao.prepareStatement(sqlDeleteAlunoTreino)) {

            // Exclui registros da tabela aluno_treino referentes ao treino
            stmtDeleteAlunoTreino.setInt(1, id);
            stmtDeleteAlunoTreino.executeUpdate();

            // Exclui o treino
            stmtDeleteTreino.setInt(1, id);
            stmtDeleteTreino.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }
}
