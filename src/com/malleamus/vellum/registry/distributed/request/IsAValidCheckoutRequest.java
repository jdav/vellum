package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.Persistings;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.response.BooleanResponse;
import com.malleamus.vellum.registry.distributed.response.ErrorResponse;

public class IsAValidCheckoutRequest implements Request {

	private Registerable r = null;
	private LocalRegistry registry = null;

	public IsAValidCheckoutRequest(Registerable r) throws VellumException {
		registry = LocalRegistry.instance();
		this.r = r;
	}

	@Override
	public Response execute() {
		try {
			boolean valid = registry.isAValidCheckOut(r);
			return new BooleanResponse(valid);
		} catch (Exception e) {
			return new ErrorResponse(e);
		}
	}

	@Override
	public void deserialize(String serial) {
		Persistings p = new Persistings(serial);
		r = registry.build(p);
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
