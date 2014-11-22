package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.response.ErrorResponse;
import com.malleamus.vellum.registry.distributed.response.SuccessResponse;

public class RegisterRequest implements Request {
	
	public Registerable t = null;
	
	public RegisterRequest(Registerable t) {
		this.t = t;
	}

	public RegisterRequest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute() {
		try {
			LocalRegistry registry = LocalRegistry.instance();
			registry.clear();
			return new SuccessResponse();
		} catch (Exception e) {
			return new ErrorResponse(e);
		}
	}

	@Override
	public void deserialize(String serial) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
