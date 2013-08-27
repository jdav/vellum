package com.malleamus.vellum;

public class VellumException extends Exception {

	public VellumException(Exception e) {
		super(e);
	}

	public VellumException(String string) {
		super(string);
	}

	public VellumException() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8864406438516044151L;

}
