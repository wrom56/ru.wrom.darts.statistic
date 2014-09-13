package ru.wrom.darts.statistic.entrypoint;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages = "ru.wrom.darts.statistic.persist.repository.crud")
@EnableTransactionManagement
@ComponentScan("ru.wrom.darts.statistic.persist.repository")
public class SpringConfig {

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
		vendorAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.H2Platform");
		//vendorAdapter.setGenerateDdl(true);
		factory.setPersistenceXmlLocation("eclipselink-persistence.xml");
		factory.setDataSource(dataSource());
		//factory.setPackagesToScan("ru.wrom.darts.statistic.persist");
		//	factory.setLoadTimeWeaver(loadTimeWeaver());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
/*
	@Bean
	public LoadTimeWeaver loadTimeWeaver() {
		return new InstrumentationLoadTimeWeaver();
	}
*/

	@Bean
	public EclipseLinkJpaDialect eclipseLinkJpaDialect() {
		return new EclipseLinkJpaDialect();
	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}


	public JdbcDataSource dataSource() {
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:./dartStatistic");
		ds.setUser("sa");
		ds.setPassword("sa");
		return ds;
	}

}