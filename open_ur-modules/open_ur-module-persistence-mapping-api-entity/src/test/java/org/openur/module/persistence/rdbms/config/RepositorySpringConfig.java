package org.openur.module.persistence.rdbms.config;

import java.sql.Driver;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Profile(value = "testRepository")
@EnableTransactionManagement
@PropertySource("classpath:/spring_test.properties")
@ImportResource("classpath:/springDataAppContext.xml")
@EnableJpaRepositories(basePackages = { "org.openur.module.persistence.rdbms.repository" })
public class RepositorySpringConfig
{
	@Inject
	protected Environment env;

	public DataSource dataSource()
	{
		DataSource ds = null;

		EmbeddedDatabase edb = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			//.addScript("classpath:/db/ddl_open_ur.sql")
			.build();

		SimpleDriverDataSource simpleDs = new SimpleDriverDataSource();
		simpleDs.setDriverClass(env.getPropertyAsClass("database.driver",
			Driver.class));
		simpleDs.setUrl(env.getProperty("database.url"));
		simpleDs.setUsername(env.getProperty("database.username"));
		simpleDs.setPassword(env.getProperty("database.password"));

		switch (env.getProperty("database.databasePlatform"))
		{
			case "org.hibernate.dialect.MySQL5InnoDBDialect":
				ds = simpleDs;
				break;

			case "org.hibernate.dialect.H2Dialect":
				ds = edb;
				break;

			default:
				break;
		}

		return ds;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory()
	{
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setJpaVendorAdapter(jpaVendorAdapter());
		em.setPackagesToScan("org.openur.module.persistence");
		em.afterPropertiesSet();

		return em.getObject();
	}

	@Bean
	public PersistenceExceptionTranslator hibernateExceptionTranslator()
	{
		return new HibernateExceptionTranslator();
	}

	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter()
	{
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(env.getProperty("database.showSql", Boolean.class));
		adapter.setGenerateDdl(env.getProperty("database.generateDdl",
			Boolean.class));
		adapter.setDatabasePlatform(env.getProperty("database.databasePlatform"));

		return adapter;
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager()
	{
		return new JpaTransactionManager(entityManagerFactory());
	}
}
