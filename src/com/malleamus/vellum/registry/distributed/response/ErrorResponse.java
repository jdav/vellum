package com.malleamus.vellum.registry.distributed.response;

import com.malleamus.diener.Response;
import com.malleamus.vellum.VellumException;

public class ErrorResponse implements Response {
	
	private Exception e = null;

	public ErrorResponse(Exception e) {
		this.e = e;
	}

	@Override
	public String serialize() {
		return "error|" + e.getLocalizedMessage();
	}

	@Override
	public void deserialize(String serial) {
		String[] components = serial.split("|");
		e = new Exception(components[1]);
	}

}
