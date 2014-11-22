package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.response.ErrorResponse;
import com.malleamus.vellum.registry.distributed.response.RegisterableResponse;

public class GetCopyRequest implements Request {
	
	private RegistrationNumber rn = null;
	
	public GetCopyRequest(RegistrationNumber rn) {
		this.rn = rn;
	}

	@Override
	public Response execute() {
		try {
			LocalRegistry registry = LocalRegistry.instance();
			Registerable r = registry.getCopy(rn);
			return new RegisterableResponse(r);
		} catch (Exception e) {
			return new ErrorResponse(e);
		}
	}

	@Override
	public void deserialize(String serial) {
		String[] components = serial.split("|");
		rn = new RegistrationNumber(components[1]);
	}

	@Override
	public String serialize() {
		return "getcopy|" + rn;
	}

}
