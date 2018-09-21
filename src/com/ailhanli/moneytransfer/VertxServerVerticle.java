package com.ailhanli.moneytransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ailhanli.moneytransfer.controller.account.AccountController;
import com.ailhanli.moneytransfer.controller.transfer.TransferController;
import com.ailhanli.moneytransfer.controller.transferlog.TransferLogController;
import com.ailhanli.moneytransfer.properties.ApplicationProperties;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/*
 * This is a entry point of vertx. all routing is done simply in this class
 * */
@Component
public class VertxServerVerticle extends AbstractVerticle {

    @Autowired
    private ApplicationProperties applicationConfiguration;
    
    @Autowired
    private  TransferController transferController;
    
    @Autowired
    private AccountController accountController ; 
    
    @Autowired
    private TransferLogController transferLogController;

    @Override
    public void start(Future<Void> future) throws Exception {
        super.start();
        
        vertx.createHttpServer().requestHandler(router()::accept).listen(applicationConfiguration.httpPort(), result -> {
			if (result.succeeded()) {
				future.complete();
			} else {
				future.fail(result.cause());
			} 
		});
    }

    private Router router() {
        Router router = Router.router(vertx);

        router.route("/api/").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html").end("<h1>Money Transfer Api</h1>");
		});
		router.route().handler(BodyHandler.create());
		
        router.get("/api/accounts").handler(accountController::getAllAccount);
		router.get("/api/accounts/:accountId").handler(accountController::getAccount);
		router.post("/api/accounts").handler(accountController::createAccount);

		router.get("/api/transfers").handler(transferLogController::getAllTransfers);
		router.get("/api/transfers/:transferId").handler(transferLogController::getTransfer);
		router.post("/api/transfers").handler(transferController::newTransfer);

        return router;
    }
}