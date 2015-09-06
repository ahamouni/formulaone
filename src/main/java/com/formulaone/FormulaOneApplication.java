package com.formulaone;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.formulaone.client.CustomResponseErrorHandler;
import com.formulaone.config.Constants;
import com.google.common.base.Joiner;

@SpringBootApplication
@ComponentScan("com.formulaone")
public class FormulaOneApplication {

	private static final Logger log = LoggerFactory.getLogger(FormulaOneApplication.class);

	@Autowired
	private Environment env;

	/**
	 * Initializes formulaone.
	 * <p/>
	 * Spring profiles can be configured with a program arguments
	 * --spring.profiles.active=your-active-profile
	 * <p/>
	 * <p>
	 * You can find more information on how profiles work with JHipster on
	 * <a href="http://jhipster.github.io/profiles.html">http://jhipster.github.
	 * io/profiles.html</a>.
	 * </p>
	 */
	@PostConstruct
	public void initApplication() throws IOException {
		if (env.getActiveProfiles().length == 0) {
			log.warn("No Spring profile configured, running with default configuration");
		} else {
			log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
			Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
			if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT)
					&& activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
				log.error("You have misconfigured your application! "
						+ "It should not run with both the 'dev' and 'prod' profiles at the same time.");
			}
			if (activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)
					&& activeProfiles.contains(Constants.SPRING_PROFILE_FAST)) {
				log.error("You have misconfigured your application! "
						+ "It should not run with both the 'prod' and 'fast' profiles at the same time.");
			}
			if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT)
					&& activeProfiles.contains(Constants.SPRING_PROFILE_CLOUD)) {
				log.error("You have misconfigured your application! "
						+ "It should not run with both the 'dev' and 'cloud' profiles at the same time.");
			}
		}
	}

	@Bean
	public ObjectMapper objectMapper() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		SimpleModule module = new SimpleModule();
		return mapper;
	}

	@Bean
	public CustomResponseErrorHandler customeResponseHandler() {
		return new CustomResponseErrorHandler();
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:i18n/messages");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}

	@Bean
	public TestRestTemplate restTemplate() {
		TestRestTemplate restTemplate = new TestRestTemplate();
		restTemplate.setErrorHandler(customeResponseHandler());
		return restTemplate;
	}

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(FormulaOneApplication.class);
		app.setShowBanner(false);
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		addDefaultProfile(app, source);
		addLiquibaseScanPackages();
		// addLiquibaseScanPackages();
		Environment env = app.run(args).getEnvironment();
		log.info(
				"Access URLs:\n----------------------------------------------------------\n\t"
						+ "Local: \t\thttp://127.0.0.1:{}\n\t"
						+ "External: \thttp://{}:{}\n----------------------------------------------------------",
				env.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));

	}

	/**
	 * If no profile has been configured, set by default the "dev" profile.
	 */
	private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
		if (!source.containsProperty("spring.profiles.active")
				&& !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {

			app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
		}
	}
	
	  /**
     * Set the liquibases.scan.packages to avoid an exception from ServiceLocator.
     */
    private static void addLiquibaseScanPackages() {
        System.setProperty("liquibase.scan.packages", Joiner.on(",").join(
            "liquibase.change", "liquibase.database", "liquibase.parser",
            "liquibase.precondition", "liquibase.datatype",
            "liquibase.serializer", "liquibase.sqlgenerator", "liquibase.executor",
            "liquibase.snapshot", "liquibase.logging", "liquibase.diff",
            "liquibase.structure", "liquibase.structurecompare", "liquibase.lockservice",
            "liquibase.ext", "liquibase.changelog"));
    }


}
