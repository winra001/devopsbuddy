package com.devopsbuddy.config;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.devopsbuddy.backend.service.UserSecurityService;
import com.devopsbuddy.web.controllers.ForgotMyPasswordController;
import com.devopsbuddy.web.controllers.SignupController;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private UserSecurityService userSecurityService;
	
	/** The encryption SALT */
	private static final String SALT = "fjiiewu2rie9u*kvn@!";

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}

	/**
	 * Public URSs.
	 */
	private static final String[] PUBLIC_MATCHERS = {
		"/webjars/**",
		"/css/**",
		"/js/**",
		"/images/**",
		"/",
		"/about/**",
		"/contact/**",
		"/error/**/*",
		"/console/**",
		ForgotMyPasswordController.FORGOT_PASSWORD_URL_MAPPING,
		ForgotMyPasswordController.CHANGE_PASSWORD_PATH,
		SignupController.SIGNUP_URL_MAPPING
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains("dev")) {
			http.csrf().disable();
			http.headers().frameOptions().disable();
		}

		http
			.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/payload")
			.failureUrl("/login?error").permitAll()
			.and()
			.logout().permitAll(); 
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*
		auth
			.inMemoryAuthentication()
			.withUser("user").password("password")
			.roles("USER");
		*/

		auth
			.userDetailsService(userSecurityService)
			.passwordEncoder(passwordEncoder());
	}

}
