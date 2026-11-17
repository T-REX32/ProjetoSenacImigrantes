package modelo.dao.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidade.usuario.Usuario;
import modelo.factory.conexao.ConexaoFactory;

public class UsuarioDAOImpl implements UsuarioDAO {

    // Inicializa diretamente para nunca vir nulo
    private ConexaoFactory fabrica = new ConexaoFactory();

    public UsuarioDAOImpl() {
        // Construtor vazio garantindo a instância
    }

    @Override
    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO app_usuario (nome, email) VALUES (?, ?)";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário", e);
        }
    }

    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE app_usuario SET nome = ?, email = ? WHERE id = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setLong(3, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    @Override
    public Usuario recuperar(Usuario usuario) {
        String sql = "SELECT id, nome, email FROM app_usuario WHERE id = ?";
        Usuario u = null;
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, usuario.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getLong("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar usuário", e);
        }
        return u;
    }

    @Override
    public void deletar(Usuario usuario) {
        String sql = "DELETE FROM app_usuario WHERE id = ?";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nome, email FROM app_usuario";
        try (Connection conexao = fabrica.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                lista.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
        return lista;
    }
}