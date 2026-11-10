package modelo.factory.conexao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ConexaoFactory {

	public SessionFactory getConexao() {

		Configuration configuracao = new Configuration();
		configuracao.addAnnotatedClass(modelo.entidade.contato.Contato.class);
		configuracao.configure("hibernate.cfg.xml");
		configuracao.setProperty("hibernate.connection.username", requiredEnvironmentVariable("DB_USERNAME"));
		configuracao.setProperty("hibernate.connection.password", requiredEnvironmentVariable("DB_PASSWORD"));

		ServiceRegistry servico = new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties()).build();
		SessionFactory fabricaSessao = configuracao.buildSessionFactory(servico);

		return fabricaSessao;
	}

	private String requiredEnvironmentVariable(String name) {
		String value = System.getenv(name);
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalStateException("Required environment variable " + name + " is not set.");
		}
		return value;
	}
}