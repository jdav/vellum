package com.malleamus.vellum.registry.distributed.response;

import com.malleamus.diener.Response;

public class SuccessResponse implements Response {

	@Override
	public String serialize() {
		return "success";
	}

	@Override
	public void deserialize(String serial) {
	}

}
