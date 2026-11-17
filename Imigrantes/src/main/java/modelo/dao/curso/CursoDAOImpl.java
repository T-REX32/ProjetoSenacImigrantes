package modelo.dao.curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidade.curso.Curso;
import modelo.factory.conexao.ConexaoFactory;

public class CursoDAOImpl implements CursoDAO {

    private ConexaoFactory fabrica;

    public CursoDAOImpl() {
        fabrica = new ConexaoFactory();
    }

    @Override
    public void inserirCurso(Curso curso) {
        String sql = "INSERT INTO app_curso (titulo, descricao, quant_turma) VALUES (?, ?, ?)";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, curso.getTitulo());
            ps.setString(2, curso.getDescricao());
            ps.setInt(3, curso.getQuantTurma());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir curso.", e);
        }
    }

    @Override
    public void deletarCurso(Curso curso) {
        String sql = "DELETE FROM app_curso WHERE id = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, curso.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar curso.", e);
        }
    }

    @Override
    public void atualizarCurso(Curso curso) {
        String sql = "UPDATE app_curso SET titulo = ?, descricao = ?, quant_turma = ? WHERE id = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, curso.getTitulo());
            ps.setString(2, curso.getDescricao());
            ps.setInt(3, curso.getQuantTurma());
            ps.setLong(4, curso.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso.", e);
        }
    }

    @Override
    public Curso recuperarCurso(Curso curso) {
        String sql = "SELECT id, titulo, descricao, quant_turma FROM app_curso WHERE id = ?";
        Curso c = null;
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, curso.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Curso();
                    c.setId(rs.getLong("id"));
                    c.setTitulo(rs.getString("titulo"));
                    c.setDescricao(rs.getString("descricao"));
                    c.setQuantTurma(rs.getInt("quant_turma"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar curso.", e);
        }
        return c;
    }

    @Override
    public List<Curso> recuperarCursos() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, descricao, quant_turma FROM app_curso";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Curso c = new Curso();
                c.setId(rs.getLong("id"));
                c.setTitulo(rs.getString("titulo"));
                c.setDescricao(rs.getString("descricao"));
                c.setQuantTurma(rs.getInt("quant_turma"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cursos.", e);
        }
        return lista;
    }
}