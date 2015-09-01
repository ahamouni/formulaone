package com.formulaone.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@ConfigurationProperties()
@ComponentScan({ "com.formulaone" })
@EnableJpaRepositories(basePackages = "com.formulaone.repository")
public class DatabaseConfig implements EnvironmentAware {

	private final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

	private RelaxedPropertyResolver dataSourcePropertyResolver;

	private RelaxedPropertyResolver liquiBasePropertyResolver;

	private Environment env;

	public DatabaseConfig() {
		super();
	}

	@Override
	public void setEnvironment(Environment env) {
		this.env = env;
		this.dataSourcePropertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
		this.liquiBasePropertyResolver = new RelaxedPropertyResolver(env, "liquiBase.");
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		LocalContainerEntityManagerFactoryBean emf = builder.dataSource(dataSource())
				.packages(new String[] { "com.formulaone.domain" }).build();

		emf.setJpaVendorAdapter(vendorAdapter);

		return emf;
	}

	@Bean
	@ConfigurationProperties(locations = "classpath:application.yml", prefix = "spring.datasource")
	@Primary
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog(liquiBasePropertyResolver.getProperty("change-log"));
		liquibase.setContexts(liquiBasePropertyResolver.getProperty("contexts"));
		liquibase.setDropFirst(Boolean.valueOf(liquiBasePropertyResolver.getProperty("drop-first")));

		if (env.acceptsProfiles(Constants.SPRING_PROFILE_FAST)) {
			if ("org.h2.jdbcx.JdbcDataSource".equals(dataSourcePropertyResolver.getProperty("dataSourceClassName"))) {
				liquibase.setShouldRun(true);
				log.warn(
						"Using '{}' profile with H2 database in memory is not optimal, you should consider switching to"
								+ " MySQL or Postgresql to avoid rebuilding your database upon each start.",
						Constants.SPRING_PROFILE_FAST);
			} else {
				liquibase.setShouldRun(false);
			}
		} else {
			log.debug("Configuring Liquibase");
		}
		return liquibase;
	}

	/**
	 * PersistenceExceptionTranslationPostProcessor is a bean post processor
	 * which adds an advisor to any bean annotated with Repository so that any
	 * platform-specific exceptions are caught and then re thrown as one
	 * Spring's unchecked data access exceptions (i.e. a subclass of
	 * DataAccessException).
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}