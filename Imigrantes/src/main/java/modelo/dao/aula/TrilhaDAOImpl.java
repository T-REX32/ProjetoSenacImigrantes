package modelo.dao.trilha;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Trilha;
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

        } catch (Exception e) {
            e.printStackTrace();

            if (sessao != null && sessao.getTransaction() != null) {
                sessao.getTransaction().rollback();
            }

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

        } catch (Exception e) {
            e.printStackTrace();

            if (sessao != null && sessao.getTransaction() != null) {
                sessao.getTransaction().rollback();
            }

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

        } catch (Exception e) {
            e.printStackTrace();

            if (sessao != null && sessao.getTransaction() != null) {
                sessao.getTransaction().rollback();
            }

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

            trilha = sessao.get(Trilha.class, id);

        } catch (Exception e) {
            e.printStackTrace();

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

            Query<Trilha> consulta = sessao.createQuery(
                "FROM Trilha", Trilha.class
            );

            trilhas = consulta.getResultList();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (sessao != null) {
                sessao.close();
            }
        }

        return trilhas;
    }
}