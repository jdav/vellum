package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.Persistings;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.response.ErrorResponse;
import com.malleamus.vellum.registry.distributed.response.SuccessResponse;

public class WithdrawRequest implements Request {

	private LocalRegistry registry = null;
	private Registerable t = null;

	public WithdrawRequest(Registerable t) throws VellumException {
		registry = LocalRegistry.instance();
		this.t = t;
	}

	@Override
	public Response execute() {
		try {
			registry.withdraw(t);
			return new SuccessResponse();
		} catch (Exception e) {
			return new ErrorResponse(e);
		}
	}

	@Override
	public void deserialize(String serial) {
		Persistings p = new Persistings(serial);
		t = registry.build(p);
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
