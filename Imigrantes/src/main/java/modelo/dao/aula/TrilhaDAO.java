package modelo.dao.trilha;

import java.util.List;
import model.Trilha;

public interface TrilhaDAO {

    void inserirTrilha(Trilha trilha);

    void atualizarTrilha(Trilha trilha);

    void deletarTrilha(Trilha trilha);

    Trilha recuperarTrilha(Long id);

    List<Trilha> recuperarTrilhas();
}