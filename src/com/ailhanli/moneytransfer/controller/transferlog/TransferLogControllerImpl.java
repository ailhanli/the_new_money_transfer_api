package com.ailhanli.moneytransfer.controller.transferlog;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.ailhanli.moneytransfer.exception.InputInvalidException;
import com.ailhanli.moneytransfer.exception.TransferNotFoundException;
import com.ailhanli.moneytransfer.model.Error;
import com.ailhanli.moneytransfer.model.SuccessMessage;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.transferlog.TransferLogService;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 * In this class simply we use services in order to complete task and then return response as JSON object in RoutingContext response.
 */
@Controller
public class TransferLogControllerImpl implements TransferLogController {

	private static Logger log = Logger.getLogger(TransferLogControllerImpl.class);

	private final TransferLogService transferLogService;

	public TransferLogControllerImpl( TransferLogService transferLogService) {
		this.transferLogService = transferLogService;
	}

	
	@Override
	public void getAllTransfers(RoutingContext routingContext) {
		try {
			List<Transfer> allTransfers = transferLogService.getAllTransfer();
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8").end(Json.encodePrettily(allTransfers));
		} catch (Exception e) {
			log.error(e);
			routingContext.response().setStatusCode(400).end(Json.encodePrettily(Error.of(e)));
		}
	}

	@Override
	public void getTransfer(RoutingContext routingContext) {
		final String transferIdAsStr = routingContext.request().getParam("transferId");
		
		try {
			final Integer transferId= Integer.valueOf(transferIdAsStr);
			Transfer transfer = transferLogService.getTransfer(transferId);
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").end(Json.encodePrettily(transfer));
		} catch (TransferNotFoundException | InputInvalidException e) {
			log.warn(e);
			routingContext.response().setStatusCode(404).end(Json.encodePrettily(Error.of(e)));
		} catch (Exception e) {
			log.error(e);
			routingContext.response().setStatusCode(400).end(Json.encodePrettily(Error.of(e)));
		}
	}


	@Override
	public void createNewTransfer(RoutingContext routingContext) {
		
		final Transfer transferToCreate = Json.decodeValue(routingContext.getBodyAsString(), Transfer.class);
		try {
			
			transferLogService.newTransferLog(transferToCreate);
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").end(Json.encodePrettily(new SuccessMessage(SuccessMessage.MessageType.CREATE)));
		} catch (Exception e) {
			log.error(e);
			routingContext.response().setStatusCode(400).end(Json.encodePrettily(Error.of(e)));
		}
	}
}