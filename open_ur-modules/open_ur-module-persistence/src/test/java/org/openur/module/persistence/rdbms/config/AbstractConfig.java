package org.openur.module.persistence.rdbms.config;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

public abstract class AbstractConfig
{
	@Inject
	protected Environment env;

	public abstract DataSource dataSource();

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
		adapter.setShowSql(env.getProperty("database.showSql",	Boolean.class));
		adapter.setGenerateDdl(env.getProperty("database.generateDdl", Boolean.class));
		adapter.setDatabasePlatform(env.getProperty("database.databasePlatform"));
		
		return adapter;
	}

	@Bean(name="transactionManager")
	public JpaTransactionManager transactionManager()
	{
		return new JpaTransactionManager(entityManagerFactory());
	}
}
