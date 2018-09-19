package com.ailhanli.moneytransfer.controller.transfer;

import io.vertx.ext.web.RoutingContext;

public interface TransferController {

	void newTransfer(RoutingContext routingContext);
}