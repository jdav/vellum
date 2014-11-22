package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.registry.distributed.response.SuccessResponse;

public class InvalidRequest implements Request {

	@Override
	public Response execute() {
		return new SuccessResponse();
	}

	@Override
	public void deserialize(String serial) {
	}

	@Override
	public String serialize() {
		return "invalid";
	}

}
