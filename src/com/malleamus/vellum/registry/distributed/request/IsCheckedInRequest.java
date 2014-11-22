package com.malleamus.vellum.registry.distributed.request;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.response.BooleanResponse;
import com.malleamus.vellum.registry.distributed.response.ErrorResponse;

public class IsCheckedInRequest implements Request {

	private RegistrationNumber rn;

	public IsCheckedInRequest(RegistrationNumber rn) {
		this.rn = rn;
	}

	@Override
	public Response execute() {
		try {
			LocalRegistry registry = LocalRegistry.instance();
			boolean r = registry.isCheckedIn(rn);
			return new BooleanResponse(r);
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
		return "ischeckedin|" + rn;
	}

}
