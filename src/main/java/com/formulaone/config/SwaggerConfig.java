package com.formulaone.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.formulaone.controller.dto.merchant.AddressRequest;
import com.formulaone.controller.dto.merchant.BankingDetailsRequest;
import com.formulaone.controller.dto.merchant.CompanyRequest;
import com.formulaone.controller.dto.merchant.GeneralRequest;
import com.formulaone.controller.dto.merchant.MerchantRequest;
import com.formulaone.controller.dto.merchant.OwnershipDetailsRequest;
import com.formulaone.controller.merchant.MerchantController;
import com.formulaone.controller.security.UserController;
import com.formulaone.domain.merchant.BankingDetails;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = { MerchantController.class,
		UserController.class, MerchantRequest.class, CompanyRequest.class,
		BankingDetailsRequest.class, AddressRequest.class,
		OwnershipDetailsRequest.class, GeneralRequest.class })
public class SwaggerConfig implements EnvironmentAware {

	private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

	public static final String DEFAULT_INCLUDE_PATTERN = "/formulaone/.*";

	private RelaxedPropertyResolver propertyResolver;

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment,
				"swagger.");
	}

	/**
	 * Swagger Springfox configuration.
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any())
				.paths(regex(DEFAULT_INCLUDE_PATTERN)).build()
				.apiInfo(apiInfo());
	}

	/**
	 * API Info as it appears on the swagger-ui page.
	 */
	private ApiInfo apiInfo() {
		return new ApiInfo(propertyResolver.getProperty("title"),
				propertyResolver.getProperty("description"),
				propertyResolver.getProperty("version"),
				propertyResolver.getProperty("termsOfServiceUrl"),
				propertyResolver.getProperty("contact"),
				propertyResolver.getProperty("license"),
				propertyResolver.getProperty("licenseUrl"));
	}

}
