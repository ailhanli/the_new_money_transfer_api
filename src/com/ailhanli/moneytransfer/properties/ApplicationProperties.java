package com.ailhanli.moneytransfer.properties;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

	private Environment environment;

	public ApplicationProperties(Environment environment) {
		super();
		this.environment = environment;
	}

	public String applicationName() {
		return environment.getProperty("spring.application.name");
	}

	public int httpPort() {
		return environment.getProperty("server.port", Integer.class);
	}

	public String getCurrencyCode() {
		return environment.getProperty("currency.code");
	}
}