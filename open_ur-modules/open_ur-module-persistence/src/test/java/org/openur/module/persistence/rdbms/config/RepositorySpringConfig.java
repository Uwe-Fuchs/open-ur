package org.openur.module.persistence.rdbms.config;

//import java.sql.Driver;
//import org.springframework.jdbc.datasource.SimpleDriverDataSource;
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
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Profile(value="testRepository")
@EnableTransactionManagement
@PropertySource("classpath:/spring_test.properties")
@ImportResource("classpath:/springDataAppContext.xml")
@EnableJpaRepositories(basePackages = {"org.openur.module.persistence.rdbms.repository"})
//@ComponentScan(basePackages = {"org.openur.module.persistence.rdbms"})
public class RepositorySpringConfig
{
	@Inject
	protected Environment env;
	
	public DataSource dataSource()
	{
		EmbeddedDatabaseFactory f = new EmbeddedDatabaseFactory();
		f.setDatabaseType(EmbeddedDatabaseType.H2);
		
		return f.getDatabase();
	}
	
//	@Override
//	public DataSource dataSource()
//	{
//		SimpleDriverDataSource ds = new SimpleDriverDataSource();
//	  ds.setDriverClass(env.getPropertyAsClass("database.driver", Driver.class));
//	  ds.setUrl(env.getProperty("database.url"));
//	  ds.setUsername(env.getProperty("database.username"));
//	  ds.setPassword(env.getProperty("database.password"));
//	  
//	  return ds;
//	}

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
