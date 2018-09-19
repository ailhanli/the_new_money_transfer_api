package com.ailhanli.moneytransfer.controller.account;


import io.vertx.ext.web.RoutingContext;

public interface AccountController {
	
	void getAccount(RoutingContext routingContext);	
	
	void getAllAccount(RoutingContext routingContext);	
}