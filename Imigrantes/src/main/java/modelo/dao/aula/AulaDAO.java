package modelo.dao.aula;

import java.util.List;

import modelo.entidade.aula.Aula;

public interface AulaDAO {

	void inserirAula(Aula aula);

	void deletarAula(Aula aula);

	void atualizarAula(Aula aula);

	Aula recuperarAula(Long id);

	List<Aula> recuperarAulas();
}