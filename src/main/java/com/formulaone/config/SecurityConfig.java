package com.formulaone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.formulaone.service.security.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * The user account that can be be used for authentication as well as the
	 * roles each user has.
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public UserDetailsService userDetailsServiceBean() {
		return new UserDetailsServiceImpl();
	}

	/**
	 * Security policies for the application - BASIC authentication is supported
	 * - CSRF headers are disabled since we are only testing the REST interface,
	 * not a web
	 *
	 * @param http
	 * @throws Exception
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Disable security
//		 http
//		 .authorizeRequests()
//		 .anyRequest().anonymous()
//		 .and()
//		 .formLogin()
//		 .and()
//		 .httpBasic().disable();

		//
		http.authorizeRequests().anyRequest().fullyAuthenticated();
		http.httpBasic();
		http.csrf().disable();

		// http.httpBasic().and().authorizeRequests().//
		// antMatchers(HttpMethod.POST, "/demo/company").hasRole("ADMIN").//
		// antMatchers(HttpMethod.POST, "/demo/user").hasRole("ADMIN").//
		// antMatchers(HttpMethod.PUT, "/demo/**").hasRole("ADMIN").//
		// antMatchers(HttpMethod.GET, "/demo/**").hasRole("ADMIN").and().//
		// csrf().disable();
	}
}
