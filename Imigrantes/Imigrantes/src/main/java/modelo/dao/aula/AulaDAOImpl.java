package modelo.dao.aula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidade.aula.Aula;
import modelo.factory.conexao.ConexaoFactory;

public class AulaDAOImpl implements AulaDAO {

    private ConexaoFactory fabrica;

    public AulaDAOImpl() {
        fabrica = new ConexaoFactory();
    }

    @Override
    public void inserirAula(Aula aula) {
        String sql = "INSERT INTO aula (trilha_id, professor_voluntario_id, titulo, conteudo, duracao_minutos) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement insert = conexao.prepareStatement(sql)) {

            insert.setLong(1, 1L); 
            insert.setLong(2, 1L); 
            insert.setString(3, aula.getTitulo());
            insert.setString(4, aula.getConteudo());
            insert.setInt(5, aula.getDuracaoMinutos());

            insert.executeUpdate();

        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao inserir aula.", erro);
        }
    }

    @Override
    public void atualizarAula(Aula aula) {
        String sql = "UPDATE aula SET titulo = ?, conteudo = ?, duracao_minutos = ? WHERE id = ?";
        
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement update = conexao.prepareStatement(sql)) {

            update.setString(1, aula.getTitulo());
            update.setString(2, aula.getConteudo());
            update.setInt(3, aula.getDuracaoMinutos());
            update.setLong(4, aula.getId());

            update.executeUpdate();

        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao atualizar aula.", erro);
        }
    }

    @Override
    public Aula recuperarAula(Aula aula) {
        String sql = "SELECT id, titulo, conteudo, duracao_minutos FROM aula WHERE id = ?";
        Aula aulaEncontrada = null;

        try (Connection conexao = fabrica.getConexao();
             PreparedStatement select = conexao.prepareStatement(sql)) {

            select.setLong(1, aula.getId());
            
            try (ResultSet rs = select.executeQuery()) {
                if (rs.next()) {
                    aulaEncontrada = new Aula();
                    aulaEncontrada.setId(rs.getLong("id"));
                    aulaEncontrada.setTitulo(rs.getString("titulo"));
                    aulaEncontrada.setConteudo(rs.getString("conteudo"));
                    aulaEncontrada.setDuracaoMinutos(rs.getInt("duracao_minutos"));
                }
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao recuperar aula.", erro);
        }

        return aulaEncontrada;
    }

    @Override
    public void deletarAula(Aula aula) {
        String sql = "DELETE FROM aula WHERE id = ?";

        try (Connection conexao = fabrica.getConexao();
             PreparedStatement delete = conexao.prepareStatement(sql)) {

            delete.setLong(1, aula.getId());
            delete.executeUpdate();

        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao deletar aula.", erro);
        }
    }

    @Override
    public List<Aula> listarAulas() {
        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, conteudo, duracao_minutos FROM aula";

        try (Connection conexao = fabrica.getConexao();
             PreparedStatement select = conexao.prepareStatement(sql);
             ResultSet rs = select.executeQuery()) {

            while (rs.next()) {
                Aula aula = new Aula();
                aula.setId(rs.getLong("id"));
                aula.setTitulo(rs.getString("titulo"));
                aula.setConteudo(rs.getString("conteudo"));
                aula.setDuracaoMinutos(rs.getInt("duracao_minutos"));
                lista.add(aula);
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao listar aulas.", erro);
        }

        return lista;
    }
}