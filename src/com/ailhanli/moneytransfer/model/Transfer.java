package com.ailhanli.moneytransfer.model;

import java.util.Currency;

public class Transfer {

	private Integer transferId;

	private Integer sourceAccountId;

	private Integer destinationAccountId;

	private double amount;

	private Currency currencyCode;

	private String comment;

	private TransferStatus status;

	public Transfer(Integer sourceAccountId, Integer destinationAccountId, double amount, Currency currencyCode,
			String comment) {
		this.sourceAccountId = sourceAccountId;
		this.destinationAccountId = destinationAccountId;
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.comment = comment;
		this.status = TransferStatus.CREATED;
	}

	public int getTransferId() {
		return transferId;
	}

	public Integer getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(Integer sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public Integer getDestinationAccountId() {
		return destinationAccountId;
	}

	public void setDestinationAccountId(Integer destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Currency getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Currency currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public TransferStatus getStatus() {
		return status;
	}

	public void setStatus(TransferStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Transfer transfer = (Transfer) o;

		return transferId == transfer.transferId;
	}

	@Override
	public int hashCode() {
		return transferId;
	}

	@Override
	public String toString() {
		return "TransferDTO{" + "transferId=" + transferId + ", sourceAccountId=" + sourceAccountId
				+ ", destinationAccountId=" + destinationAccountId + ", amount=" + amount + ", currencyCode="
				+ currencyCode + ", comment='" + comment + '\'' + ", status=" + status + '}';
	}

	public enum TransferStatus {
		CREATED, EXECUTED, FAILED
	}
}
