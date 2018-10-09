package com.ailhanli.moneytransfer.model;

public class SuccessMessage {

	private String message;

	public SuccessMessage(MessageType messageType) {
		super();
		switch (messageType) {
		case CREATE:
			message="It is created successfully.";
			break;
		case DELETE:
			message="It is updated successfully.";
			break;
		case UPDATE:
			message="It is deleted successfully.";
			break;

		default:
			break;
		}
	}
	
	public String getMessage() {
		return message;
	}
	
	public enum MessageType{
		CREATE, DELETE, UPDATE
	}
}