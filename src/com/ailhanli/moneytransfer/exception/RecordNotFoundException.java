package com.ailhanli.moneytransfer.exception;

public class RecordNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Integer recordId;

	public RecordNotFoundException(Integer recordId) {
		super();
		this.recordId = recordId;
	}
	
	public Integer getRecordId() {
		return recordId;
	}
}