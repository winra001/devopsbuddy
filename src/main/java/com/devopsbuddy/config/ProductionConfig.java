package com.devopsbuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.devopsbuddy.backend.service.EmailService;
import com.devopsbuddy.backend.service.SmtpEmailService;

@Configuration
@Profile("prod")
@PropertySource("file:///${user.home}/Documents/Development/workspace-sts/devopsbuddy_prop/application-prod.properties")
public class ProductionConfig {

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
