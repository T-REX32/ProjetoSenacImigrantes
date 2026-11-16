package modelo.dao.trilha;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import modelo.entidade.trilha.Trilha;
import modelo.factory.conexao.ConexaoFactory;

public class TrilhaDAOImpl implements TrilhaDAO {

	private ConexaoFactory fabrica;

	public TrilhaDAOImpl() {
		fabrica = new ConexaoFactory();
	}

	@Override
	public void inserirTrilha(Trilha trilha) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.save(trilha);
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
	public void deletarTrilha(Trilha trilha) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.delete(trilha);
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
	public void atualizarTrilha(Trilha trilha) {
		Session sessao = null;
		try {
			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();
			sessao.update(trilha);
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
	public Trilha recuperarTrilha(Long id) {
		Session sessao = null;
		Trilha trilha = null;
		try {
			sessao = fabrica.getConexao().openSession();
			CriteriaBuilder construtor = sessao.getCriteriaBuilder();
			CriteriaQuery<Trilha> criteria = construtor.createQuery(Trilha.class);
			Root<Trilha> raizTrilha = criteria.from(Trilha.class);
			criteria.select(raizTrilha);
			criteria.where(construtor.equal(raizTrilha.get("id"), id));
			List<Trilha> trilhas = sessao.createQuery(criteria).getResultList();
			if (!trilhas.isEmpty()) {
				trilha = trilhas.get(0);
			}
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
		return trilha;
	}

	@Override
	public List<Trilha> recuperarTrilhas() {
		Session sessao = null;
		List<Trilha> trilhas = null;
		try {
			sessao = fabrica.getConexao().openSession();
			CriteriaBuilder construtor = sessao.getCriteriaBuilder();
			CriteriaQuery<Trilha> criteria = construtor.createQuery(Trilha.class);
			Root<Trilha> raizTrilha = criteria.from(Trilha.class);
			criteria.select(raizTrilha);
			trilhas = sessao.createQuery(criteria).getResultList();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			throw erro;
		} finally {
			if (sessao != null) {
				sessao.close();
			}
		}
		return trilhas;
	}
}
