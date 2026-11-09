package modelo.dao.aula;


import org.hibernate.Session;

import modelo.entidade.contato.Aula;
import modelo.factory.conexao.ConexaoFactory;

public class AulaDAOImpl implements AulaDAO {

	private ConexaoFactory fabrica;

	public AulaDAOImpl() {
		fabrica = new ConexaoFactory();
	}

	public void inserirAula(Aula aula) {

		Session sessao = null;

		try {

			sessao = fabrica.getConexao().openSession();
			sessao.beginTransaction();

			sessao.save(aula);

			sessao.getTransaction().commit();

		} catch (Exception sqlException) {

			sqlException.printStackTrace();

			if (sessao.getTransaction() != null) {
				sessao.getTransaction().rollback();
			}

		} finally {

			if (sessao != null) {
				sessao.close();
			}
		}
	}

	@Override
	public void inserirContato(Aula aula) {
		// TODO Auto-generated method stub
		
	}

	// public void deletarContato(Contato contato) {
//  Session sessao = null;
//  try {
//      sessao = fabrica.getConexao().openSession();
//      sessao.beginTransaction();
//      sessao.delete(contato);
//      sessao.getTransaction().commit();
//  } catch (Exception sqlException) {
//      sqlException.printStackTrace();
//      if (sessao.getTransaction() != null) {
//          sessao.getTransaction().rollback();
//      }
//  } finally {
//      if (sessao != null) {
//          sessao.close();
//      }
//  }
//}

//public void atualizarContato(Contato contato) {
//  Session sessao = null;
//  try {
//      sessao = fabrica.getConexao().openSession();
//      sessao.beginTransaction();
//      sessao.update(contato);
//      sessao.getTransaction().commit();
//  } catch (Exception sqlException) {
//      sqlException.printStackTrace();
//      if (sessao.getTransaction() != null) {
//          sessao.getTransaction().rollback();
//      }
//  } finally {
//      if (sessao != null) {
//          sessao.close();
//      }
//  }
//}

//public Contato recuperarContato(Contato contato) {
//  Session sessao = null;
//  Contato contatoRecuperado = null;
//  try {
//      sessao = fabrica.getConexao().openSession();
//      sessao.beginTransaction();
//      CriteriaBuilder construtor = sessao.getCriteriaBuilder();
//      CriteriaQuery<Contato> criteria = construtor.createQuery(Contato.class);
//      Root<Contato> raizContato = criteria.from(Contato.class);
//      criteria.select(raizContato);
//      ParameterExpression<Long> idContato = construtor.parameter(Long.class);
//      criteria.where(construtor.equal(raizContato.get("id"), idContato));
//      contatoRecuperado = sessao.createQuery(criteria).setParameter(idContato, contato.getId()).getSingleResult();
//      sessao.getTransaction().commit();
//  } catch (Exception sqlException) {
//      sqlException.printStackTrace();
//      if (sessao.getTransaction() != null) {
//          sessao.getTransaction().rollback();
//      }
//  } finally {
//      if (sessao != null) {
//          sessao.close();
//      }
//  }
//  return contatoRecuperado;
//}

//public List<Contato> recuperarContatos() {
//  Session sessao = null;
//  List<Contato> contatos = null;
//  try {
//      sessao = fabrica.getConexao().openSession();
//      sessao.beginTransaction();
//      CriteriaBuilder construtor = sessao.getCriteriaBuilder();
//      CriteriaQuery<Contato> criteria = construtor.createQuery(Contato.class);
//      Root<Contato> raizContato = criteria.from(Contato.class);
//      criteria.select(raizContato);
//      contatos = sessao.createQuery(criteria).getResultList();
//      sessao.getTransaction().commit();
//  } catch (Exception sqlException) {
//      sqlException.printStackTrace();
//      if (sessao.getTransaction() != null) {
//          sessao.getTransaction().rollback();
//      }
//  } finally {
//      if (sessao != null) {
//          sessao.close();
//      }
//  }
//  return contatos;
//}
}