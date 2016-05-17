package org.openur.remoting.webservice.config;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ImportResource("classpath:/springDataAppContext.xml")
@EnableJpaRepositories(basePackages = { "org.openur.module.persistence.rdbms.repository" })
public class JpaRepositorySpringConfig
{
	@Inject
	protected Environment env;

	public DataSource dataSource()
	{
    final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
    dsLookup.setResourceRef(true);
    DataSource dataSource = dsLookup.getDataSource(env.getProperty("database.jndiName", "java:comp/env/jdbc/open_ur"));
    
    return dataSource;
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
		adapter.setShowSql(env.getProperty("database.showSql", Boolean.class, Boolean.FALSE));
		adapter.setDatabasePlatform(env.getProperty("database.databasePlatform", "org.hibernate.dialect.MySQL5InnoDBDialect"));

		return adapter;
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager()
	{
		return new JpaTransactionManager(entityManagerFactory());
	}
}
