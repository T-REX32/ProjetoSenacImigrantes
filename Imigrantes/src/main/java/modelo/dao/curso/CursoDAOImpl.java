package modelo.dao.curso;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import modelo.entidade.curso.Curso;
import modelo.factory.conexao.ConexaoFactory;

public class CursoDAOImpl implements CursoDAO {

	private ConexaoFactory fabrica;

	public CursoDAOImpl() {
		fabrica = new ConexaoFactory();
	}

	@Override
	public void inserirCurso(Curso curso) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.save(curso);
			sessao.getTransaction().commit();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			if (sessao != null && sessao.getTransaction() != null
					&& sessao.getTransaction().isActive()) {
				sessao.getTransaction().rollback();
			}
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
	}

	@Override
	public void deletarCurso(Curso curso) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.delete(curso);
			sessao.getTransaction().commit();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			if (sessao != null && sessao.getTransaction() != null
					&& sessao.getTransaction().isActive()) {
				sessao.getTransaction().rollback();
			}
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
	}

	@Override
	public void atualizarCurso(Curso curso) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.update(curso);
			sessao.getTransaction().commit();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			if (sessao != null && sessao.getTransaction() != null
					&& sessao.getTransaction().isActive()) {
				sessao.getTransaction().rollback();
			}
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
	}

	@Override
	public Curso recuperarCurso(Long id) {
		Session sessao = null;
		Curso curso = null;
		try {
			sessao = fabrica.getConexao().openSession();
			CriteriaBuilder construtor = sessao.getCriteriaBuilder();
			CriteriaQuery<Curso> criteria = construtor.createQuery(Curso.class);
			Root<Curso> raizCurso = criteria.from(Curso.class);
			criteria.select(raizCurso);
			criteria.where(construtor.equal(raizCurso.get("id"), id));
			List<Curso> cursos = sessao.createQuery(criteria).getResultList();
			if (!cursos.isEmpty()) {
				curso = cursos.get(0);
			}
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
		return curso;
	}

	@Override
	public List<Curso> recuperarCursos() {
		Session sessao = null;
		List<Curso> cursos = null;
		try {
			sessao = fabrica.getConexao().openSession();
			CriteriaBuilder construtor = sessao.getCriteriaBuilder();
			CriteriaQuery<Curso> criteria = construtor.createQuery(Curso.class);
			Root<Curso> raizCurso = criteria.from(Curso.class);
			criteria.select(raizCurso);
			cursos = sessao.createQuery(criteria).getResultList();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
		return cursos;
	}
}
