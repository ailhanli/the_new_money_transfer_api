package com.ailhanli.moneytransfer.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Transfer {

	private static final AtomicInteger COUNTER = new AtomicInteger(0);

	private final Integer transferId;

	private Integer sourceAccountId;

	private Integer destinationAccountId;

	private BigDecimal amount;

	private String currencyCode;

	private String comment;

	private TransferStatus status;

	public Transfer(Integer sourceAccountId, Integer destinationAccountId, BigDecimal amount, String currencyCode,
			String comment) {
		this.transferId = COUNTER.getAndIncrement();
		this.sourceAccountId = sourceAccountId;
		this.destinationAccountId = destinationAccountId;
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.comment = comment;
		this.status = TransferStatus.CREATED;
	}

	public Transfer() {
		this.transferId = COUNTER.getAndIncrement();
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public static AtomicInteger getCounter() {
		return COUNTER;
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
		return "TransferDTO{" + "transferId=" + transferId + ", sourceAccountId=" + sourceAccountId + ", destinationAccountId="
				+ destinationAccountId + ", amount=" + amount + ", currencyCode=" + currencyCode + ", comment='" + comment
				+ '\'' + ", status=" + status + '}';
	}

	public enum TransferStatus {
		CREATED, EXECUTED, FAILED
	}
}
