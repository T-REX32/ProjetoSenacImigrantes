package modelo.dao.turma;

import java.util.List;

import modelo.entidade.turma.Turma;

public interface TurmaDAO {

	void inserirTurma(Turma turma);

	void deletarTurma(Turma turma);

	void atualizarTurma(Turma turma);

	Turma recuperarTurma(Long id);

	List<Turma> recuperarTurmas();
}
