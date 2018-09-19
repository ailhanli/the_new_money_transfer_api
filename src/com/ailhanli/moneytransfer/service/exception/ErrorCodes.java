package com.ailhanli.moneytransfer.service.exception;

public interface ErrorCodes {

	String ERROR_CODE_ACCOUNT_NOT_FOUND="1";
	String ERROR_CODE_INSUFFICIENT_BALANCE="2";
	String ERROR_CODE_TRANSFER_NOT_FOUND="3";
	String ERROR_CODE_UNKNOWN_ERROR="999";
	String ERROR_CODE_INVALID_INPUT="-1";
	String ERROR_CODE_SYSTEM_ERROR="-2";
}