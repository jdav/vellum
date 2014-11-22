package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.response.ErrorResponse;
import com.malleamus.vellum.registry.distributed.response.SuccessResponse;

public class LoadRequest implements Request {
	
	private LocalRegistry registry = null;
	
	public LoadRequest() throws VellumException {
		registry = LocalRegistry.instance();
	}

	@Override
	public Response execute() {
		try {
			registry.load();
			return new SuccessResponse();
		} catch (VellumException e) {
			return new ErrorResponse(e);
		}
	}

	@Override
	public String serialize() {
		return "load";
	}

	@Override
	public void deserialize(String serial) {
	}

}
