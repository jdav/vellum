package com.malleamus.vellum.registry.distributed.response;

import com.malleamus.diener.Response;

public class BooleanResponse implements Response {
	
	boolean r = false;
	
	public BooleanResponse(boolean r) {
		this.r = r;
	}

	public boolean getBoolean() {
		return r;
	}
	
	@Override
	public String serialize() {
		return null;
	}

	@Override
	public void deserialize(String serial) {
	}

}
