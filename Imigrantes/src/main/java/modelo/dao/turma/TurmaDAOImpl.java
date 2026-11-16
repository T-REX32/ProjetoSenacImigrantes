package modelo.dao.turma;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import modelo.entidade.turma.Turma;
import modelo.factory.conexao.ConexaoFactory;

public class TurmaDAOImpl implements TurmaDAO {

	private ConexaoFactory fabrica;

	public TurmaDAOImpl() {
		fabrica = new ConexaoFactory();
	}

	@Override
	public void inserirTurma(Turma turma) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.save(turma);
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
	public void deletarTurma(Turma turma) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.delete(turma);
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
	public void atualizarTurma(Turma turma) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.update(turma);
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
	public Turma recuperarTurma(Long id) {
		Session sessao = null;
		Turma turma = null;
		try {
			sessao = fabrica.getConexao().openSession();
			CriteriaBuilder construtor = sessao.getCriteriaBuilder();
			CriteriaQuery<Turma> criteria = construtor.createQuery(Turma.class);
			Root<Turma> raizTurma = criteria.from(Turma.class);
			criteria.select(raizTurma);
			criteria.where(construtor.equal(raizTurma.get("id"), id));
			List<Turma> turmas = sessao.createQuery(criteria).getResultList();
			if (!turmas.isEmpty()) {
				turma = turmas.get(0);
			}
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
		return turma;
	}

	@Override
	public List<Turma> recuperarTurmas() {
		Session sessao = null;
		List<Turma> turmas = null;
		try {
			sessao = fabrica.getConexao().openSession();
			CriteriaBuilder construtor = sessao.getCriteriaBuilder();
			CriteriaQuery<Turma> criteria = construtor.createQuery(Turma.class);
			Root<Turma> raizTurma = criteria.from(Turma.class);
			criteria.select(raizTurma);
			turmas = sessao.createQuery(criteria).getResultList();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
		return turmas;
	}
}
