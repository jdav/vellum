package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.Persistings;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.response.ErrorResponse;
import com.malleamus.vellum.registry.distributed.response.SuccessResponse;

public class CheckinRequest implements Request {
	
	private Registerable r = null;
	private LocalRegistry registry = null;
	
	public CheckinRequest(Registerable r) throws VellumException {
		registry = LocalRegistry.instance();
		this.r = r;
	}

	@Override
	public Response execute() {
		try {
			registry.checkIn(r);
			return new SuccessResponse();
		} catch (Exception e) {
			return new ErrorResponse(e);
		}
	}

	@Override
	public String serialize() {
		return "checkin|" + r.serialize();
	}

	@Override
	//TODO: Shouldn't swallow these exceptions...
	public void deserialize(String serial) {
		Persistings p = new Persistings(serial);
		r = registry.build(p);
	}

}
