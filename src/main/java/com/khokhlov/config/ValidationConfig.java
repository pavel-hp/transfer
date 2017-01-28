package com.khokhlov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;

/**
 * @author Khokhlov Pavel
 */
@Configuration
public class ValidationConfig {

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {

		MethodValidationPostProcessor processor =
				new MethodValidationPostProcessor();
		processor.setValidator(validator());
		return processor;
	}

	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}
}