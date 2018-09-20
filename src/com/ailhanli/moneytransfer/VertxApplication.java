package com.ailhanli.moneytransfer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.vertx.core.Vertx;

/*
 * This is a entry point of application
 * */
@SpringBootApplication
@Configuration("application-context-prod.xml")
public class VertxApplication {

	@Autowired
	private VertxServerVerticle vertxServerVerticle;
	
	public static void main(String[] args) {
		SpringApplication.run(VertxApplication.class);
	}

	@PostConstruct
	public void deployServerVerticle() {
		Vertx.vertx().deployVerticle(vertxServerVerticle);
	}
}