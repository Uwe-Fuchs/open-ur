package org.openur.module.persistence.rdbms.config;

import java.sql.Driver;

import javax.sql.DataSource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Profile(value="test")
@EnableTransactionManagement
@PropertySource("classpath:/spring_test.properties")
@ImportResource("classpath:/springDataAppContext.xml")
@EnableJpaRepositories(basePackages = {"org.openur.module.persistence.rdbms.repository"})
@ComponentScan(basePackages = {"org.openur.module.persistence.rdbms"})
public class RepositoryConfig
	extends AbstractConfig
{
	@Override
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
//    ds.setDriverClass(env.getPropertyAsClass("database.driver", Driver.class));
//    ds.setUrl(env.getProperty("database.url"));
//    ds.setUsername(env.getProperty("database.username"));
//    ds.setPassword(env.getProperty("database.password"));
//    
//    return ds;
//	}
}
