package modelo.dao.usuario;

import java.util.List;
import modelo.entidade.usuario.Usuario;

public interface UsuarioDAO {
    void inserir(Usuario usuario);
    void atualizar(Usuario usuario);
    Usuario recuperar(Usuario usuario);
    void deletar(Usuario usuario);
    List<Usuario> listar();
}