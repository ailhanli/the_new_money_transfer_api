package com.ailhanli.moneytransfer.controller.transferlog;

import io.vertx.ext.web.RoutingContext;

public interface TransferLogController {
		
	void getTransfer(RoutingContext routingContext);

	void getAllTransfers(RoutingContext routingContext);	
}