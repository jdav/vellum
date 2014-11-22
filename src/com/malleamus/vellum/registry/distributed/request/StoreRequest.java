package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.response.ErrorResponse;
import com.malleamus.vellum.registry.distributed.response.SuccessResponse;

public class StoreRequest implements Request {

	@Override
	public Response execute() {
		try {
			LocalRegistry registry = LocalRegistry.instance();
			registry.load();
			return new SuccessResponse();
		} catch (Exception e) {
			return new ErrorResponse(e);
		}
	}

	@Override
	public String serialize() {
		return "store";
	}

	@Override
	public void deserialize(String serial) {
	}

}
