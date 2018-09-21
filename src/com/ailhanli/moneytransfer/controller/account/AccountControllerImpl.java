package com.ailhanli.moneytransfer.controller.account;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.ailhanli.moneytransfer.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.exception.InputInvalidException;
import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.model.Error;
import com.ailhanli.moneytransfer.service.account.AccountService;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 * In this class simply we use services in order to complete task and then
 * return response as JSON object in RoutingContext response.
 */
@Controller
public class AccountControllerImpl implements AccountController {
	private static Logger log = Logger.getLogger(AccountControllerImpl.class);

	private final AccountService accountService;

	public AccountControllerImpl(AccountService accountService) {
		this.accountService = accountService;
	}

	public void getAccount(RoutingContext routingContext) {
		final String accountIdAsString = routingContext.request().getParam("accountId");

		try {
			final Integer accountId = Integer.valueOf(accountIdAsString);

			Account account = accountService.getAccount(accountId);
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(account));
		} catch (AccountNotFoundException | InputInvalidException e) {
			log.warn(e);
			routingContext.response().setStatusCode(404).end(Json.encodePrettily(Error.of(e)));
		} catch (Exception e) {
			log.error(e);
			routingContext.response().setStatusCode(400).end(Json.encodePrettily(Error.of(e)));
		}
	}

	@Override
	public void getAllAccount(RoutingContext routingContext) {
		try {
			List<Account> allAccounts = accountService.getAllAccounts();
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(allAccounts));
		} catch (Exception e) {
			log.error(e);
			routingContext.response().setStatusCode(400).end(Json.encodePrettily(Error.of(e)));
		}
	}

	@Override
	@Transactional
	public void createAccount(RoutingContext routingContext) {
		try {
			final Account account = Json.decodeValue(routingContext.getBodyAsString(), Account.class);
			log.debug(account);

			Integer accountId= accountService.createNewAccount(account);
			Account accountCreated= accountService.getAccount(accountId);
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(accountCreated));
		} catch (Exception e) {
			log.error(e);
			routingContext.response().setStatusCode(400).end(Json.encodePrettily(Error.of(e)));
		}
	}
}