package com.formulaone.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * Database configuration for Heroku
 */
@Configuration
@Profile(Constants.SPRING_PROFILE_HEROKU)
public class HerokuDatabaseConfig implements EnvironmentAware {

	private static final String HEROKU_DATABASE_UR = "DATABASE_URL";

	private final Logger log = LoggerFactory
			.getLogger(HerokuDatabaseConfig.class);

	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Bean
	public DataSource dataSource() {
		log.debug("Configuring Heroku Datasource");

		String herokuUrl = env.getProperty(HEROKU_DATABASE_UR);
		if (herokuUrl != null) {
			
			log.info("Using Heroku database url (" + herokuUrl
					+ ")  to use it with JDBC");

			URI dbUri = null;
			try {
				dbUri = new URI(herokuUrl);
			} catch (URISyntaxException e) {
				log.error("Exception while processing heroku url ", e);
				throw new ApplicationContextException(
						"Heroku database connection pool is not configured correctly");
			}

			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
					+ String.valueOf(dbUri.getPort()) + dbUri.getPath()
					+ "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

			log.info("DBURL is: " + dbUrl);

			BasicDataSource basicDataSource = new BasicDataSource();
			basicDataSource.setUrl(dbUrl);
			basicDataSource.setUsername(username);
			basicDataSource.setPassword(password);

			return basicDataSource;

		} else {
			log.error(
					"Heroku database URL is not configured, you must set --spring.datasource.heroku-url=$DATABASE_URL");
			throw new ApplicationContextException(
					"Heroku database URL is not configured, you must set --spring.datasource.heroku-url=$DATABASE_URL");
		}
	}

}
