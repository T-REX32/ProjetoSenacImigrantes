package modelo.dao.aula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
        String sql = "INSERT INTO app_aula (titulo, conteudo, duracao_minutos, trilha_id, "
                + "professor_voluntario_id, disponivel) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement insert = conexao.prepareStatement(sql)) {

            insert.setString(1, aula.getTitulo());
            insert.setString(2, aula.getConteudo());
            insert.setInt(3, aula.getDuracaoMinutos());
            setNullableLong(insert, 4, aula.getTrilhaId());
            setNullableLong(insert, 5, aula.getProfessorVoluntarioId());
            insert.setBoolean(6, aula.isDisponivel());

            insert.executeUpdate();

        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao inserir aula.", erro);
        }
    }

    @Override
    public void atualizarAula(Aula aula) {
        String sql = "UPDATE app_aula SET titulo = ?, conteudo = ?, duracao_minutos = ?, "
                + "trilha_id = ?, professor_voluntario_id = ?, disponivel = ? WHERE id = ?";
        
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement update = conexao.prepareStatement(sql)) {

            update.setString(1, aula.getTitulo());
            update.setString(2, aula.getConteudo());
            update.setInt(3, aula.getDuracaoMinutos());
            setNullableLong(update, 4, aula.getTrilhaId());
            setNullableLong(update, 5, aula.getProfessorVoluntarioId());
            update.setBoolean(6, aula.isDisponivel());
            update.setLong(7, aula.getId());

            update.executeUpdate();

        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao atualizar aula.", erro);
        }
    }

    @Override
    public Aula recuperarAula(Aula aula) {
        String sql = "SELECT id, titulo, conteudo, duracao_minutos, trilha_id, "
                + "professor_voluntario_id, disponivel FROM app_aula WHERE id = ?";
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
                    aulaEncontrada.setTrilhaId(nullableLong(rs, "trilha_id"));
                    aulaEncontrada.setProfessorVoluntarioId(nullableLong(rs, "professor_voluntario_id"));
                    aulaEncontrada.setDisponivel(rs.getBoolean("disponivel"));
                }
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao recuperar aula.", erro);
        }

        return aulaEncontrada;
    }

    @Override
    public void deletarAula(Aula aula) {
        String sql = "DELETE FROM app_aula WHERE id = ?";

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
        String sql = "SELECT id, titulo, conteudo, duracao_minutos, trilha_id, "
                + "professor_voluntario_id, disponivel FROM app_aula";

        try (Connection conexao = fabrica.getConexao();
             PreparedStatement select = conexao.prepareStatement(sql);
             ResultSet rs = select.executeQuery()) {

            while (rs.next()) {
                Aula aula = new Aula();
                aula.setId(rs.getLong("id"));
                aula.setTitulo(rs.getString("titulo"));
                aula.setConteudo(rs.getString("conteudo"));
                aula.setDuracaoMinutos(rs.getInt("duracao_minutos"));
                aula.setTrilhaId(nullableLong(rs, "trilha_id"));
                aula.setProfessorVoluntarioId(nullableLong(rs, "professor_voluntario_id"));
                aula.setDisponivel(rs.getBoolean("disponivel"));
                lista.add(aula);
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao listar aulas.", erro);
        }

        return lista;
    }

    private void setNullableLong(PreparedStatement statement, int index, Long value)
            throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.BIGINT);
        } else {
            statement.setLong(index, value);
        }
    }

    private Long nullableLong(ResultSet resultSet, String column) throws SQLException {
        long value = resultSet.getLong(column);
        return resultSet.wasNull() ? null : value;
    }
}