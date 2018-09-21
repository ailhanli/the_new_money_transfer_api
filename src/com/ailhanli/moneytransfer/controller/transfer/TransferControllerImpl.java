package com.ailhanli.moneytransfer.controller.transfer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.ailhanli.moneytransfer.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.exception.InputInvalidException;
import com.ailhanli.moneytransfer.exception.InsufficientBalanceException;
import com.ailhanli.moneytransfer.model.Error;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.transfer.TransferService;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 * In this class simply we use services in order to complete task and then return response as JSON object in RoutingContext response.
 */
@Controller
public class TransferControllerImpl implements TransferController {

	private static Logger log = Logger.getLogger(TransferControllerImpl.class);

	private final TransferService transferService;

	public TransferControllerImpl(TransferService transferService) {
		super();
		this.transferService = transferService;
	}

	@Override
	public void newTransfer(RoutingContext routingContext) {
		try {
			final Transfer transfer = Json.decodeValue(routingContext.getBodyAsString(), Transfer.class);
			log.debug(transfer);

			transferService.transferMoney(transfer);

			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(transfer));
		} catch (InsufficientBalanceException | AccountNotFoundException | InputInvalidException
				| GeneralSystemException e) {
			log.warn(e);
			routingContext.response().setStatusCode(404).end(Json.encodePrettily(Error.of(e)));
		} catch (Exception e) {
			log.error(e);
			routingContext.response().setStatusCode(400).end(Json.encodePrettily(Error.of(e)));
		}
	}
}