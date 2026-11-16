package modelo.dao.aula;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import modelo.entidade.aula.Aula;
import modelo.factory.conexao.ConexaoFactory;

public class AulaDAOImpl implements AulaDAO {

	private ConexaoFactory fabrica;

	public AulaDAOImpl() {
		fabrica = new ConexaoFactory();
	}

	@Override
	public void inserirAula(Aula aula) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.save(aula);
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
	public void deletarAula(Aula aula) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.delete(aula);
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
	public void atualizarAula(Aula aula) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.update(aula);
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
	public Aula recuperarAula(Long id) {
		Session sessao = null;
		Aula aula = null;
		try {
			sessao = fabrica.getConexao().openSession();
			CriteriaBuilder construtor = sessao.getCriteriaBuilder();
			CriteriaQuery<Aula> criteria = construtor.createQuery(Aula.class);
			Root<Aula> raizAula = criteria.from(Aula.class);
			criteria.select(raizAula);
			criteria.where(construtor.equal(raizAula.get("id"), id));
			List<Aula> aulas = sessao.createQuery(criteria).getResultList();
			if (!aulas.isEmpty()) {
				aula = aulas.get(0);
			}
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
		return aula;
	}

	@Override
	public List<Aula> recuperarAulas() {
		Session sessao = null;
		List<Aula> aulas = null;
		try {
			sessao = fabrica.getConexao().openSession();
			CriteriaBuilder construtor = sessao.getCriteriaBuilder();
			CriteriaQuery<Aula> criteria = construtor.createQuery(Aula.class);
			Root<Aula> raizAula = criteria.from(Aula.class);
			criteria.select(raizAula);
			aulas = sessao.createQuery(criteria).getResultList();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
		return aulas;
	}
}