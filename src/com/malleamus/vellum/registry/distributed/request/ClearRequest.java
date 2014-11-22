package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.response.SuccessResponse;

public class ClearRequest implements Request {
	
	private LocalRegistry registry = null;
	
	public ClearRequest() throws VellumException {
		registry = LocalRegistry.instance();
	}

	@Override
	public Response execute() {
		registry.clear();
		return new SuccessResponse();
	}

	@Override
	public void deserialize(String serial) {
	}

	@Override
	public String serialize() {
		return "clear";
	}

}
