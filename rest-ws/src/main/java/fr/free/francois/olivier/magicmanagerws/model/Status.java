package fr.free.francois.olivier.magicmanagerws.model;

public class Status {

	private int code;
	
	private String message;

	public Status () {}
	
	public Status ( int code, String message ) {
		this.setCode(code);
		this.setMessage(message);
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
