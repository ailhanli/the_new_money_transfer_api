package com.ailhanli.moneytransfer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.vertx.core.Vertx;

/*
 * This is a entry point of application
 * */
@SpringBootApplication
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