package modelo.dao.trilha;

import java.util.List;

import modelo.entidade.trilha.Trilha;

public interface TrilhaDAO {

	void inserirTrilha(Trilha trilha);

	void deletarTrilha(Trilha trilha);

	void atualizarTrilha(Trilha trilha);

	Trilha recuperarTrilha(Long id);

	List<Trilha> recuperarTrilhas();
}