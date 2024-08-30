package br.com.controle;

import br.com.modelo.Aluno;
import br.com.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoController {

    private Connection conexao; 

    // Método para cadastrar um novo aluno
    public void cadastrarAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, genero, endereco, telefone, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getGenero());
            stmt.setString(3, aluno.getEndereco());
            stmt.setString(4, aluno.getTelefone());
            stmt.setString(5, aluno.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }

    // Método para obter a lista de todos os alunos
    public List<Aluno> listarAlunos() {
        List<Aluno> listaAlunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setGenero(rs.getString("genero"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setEmail(rs.getString("email"));
                aluno.setEndereco(rs.getString("endereco"));

                listaAlunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }

        return listaAlunos;
    }

    // Método para obter um aluno por ID
    public Aluno obterAlunoPorId(int id) {
        Aluno aluno = null;
        String sql = "SELECT * FROM aluno WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aluno = new Aluno();
                    aluno.setId(rs.getInt("id"));
                    aluno.setNome(rs.getString("nome"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }

        return aluno;
    }

    // Método para atualizar um aluno
    public void atualizarAluno(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, genero = ?, endereco = ?, telefone = ?, email = ? WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getGenero());
            stmt.setString(3, aluno.getEndereco());
            stmt.setString(4, aluno.getTelefone());
            stmt.setString(5, aluno.getEmail());
            stmt.setInt(6, aluno.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }

    // Método para excluir um aluno por ID
    public void excluirAluno(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conexao = ConexaoBD.obterConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }
}
