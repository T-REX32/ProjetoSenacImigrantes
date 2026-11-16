package modelo.factory.conexao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import modelo.entidade.aula.Aula;
import modelo.entidade.curso.Curso;
import modelo.entidade.trilha.Trilha;
import modelo.entidade.turma.Turma;

public class ConexaoFactory {

	public SessionFactory getConexao() {
		return SessionFactoryHolder.SESSION_FACTORY;
	}

	private static SessionFactory criarSessionFactory() {
		Configuration configuracao = new Configuration();
		configuracao.configure("hibernate.cfg.xml");
		configuracao.addAnnotatedClass(Aula.class);
		configuracao.addAnnotatedClass(Curso.class);
		configuracao.addAnnotatedClass(Trilha.class);
		configuracao.addAnnotatedClass(Turma.class);
		configuracao.setProperty("hibernate.connection.username", requiredEnvironmentVariable("DB_USERNAME"));
		configuracao.setProperty("hibernate.connection.password", requiredEnvironmentVariable("DB_PASSWORD"));

		ServiceRegistry servico = new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties()).build();
		return configuracao.buildSessionFactory(servico);
	}

	private static String requiredEnvironmentVariable(String name) {
		String value = System.getenv(name);
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalStateException("Required environment variable " + name + " is not set.");
		}
		return value;
	}

	private static class SessionFactoryHolder {
		private static final SessionFactory SESSION_FACTORY = criarSessionFactory();
	}
}