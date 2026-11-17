package modelo.dao.curso;

import java.util.List;

import modelo.entidade.curso.Curso;

public interface CursoDAO {

	void inserirCurso(Curso curso);

	void deletarCurso(Curso curso);

	void atualizarCurso(Curso curso);

	Curso recuperarCurso(Curso curso);

	List<Curso> recuperarCursos();
}
