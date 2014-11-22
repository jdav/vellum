package com.malleamus.vellum.registry.distributed.response;

import com.malleamus.diener.Response;
import com.malleamus.vellum.Registerable;

public class RegisterableResponse implements Response {
	
	private Registerable r = null;

	public RegisterableResponse(Registerable r) {
		this.r = r;
	}
	
	public Registerable getRegisterable() {
		return r;
	}
	
	public void setRegisterable(Registerable r) {
		this.r = r;
	}

	@Override
	public String serialize() {
		return r.getRegistrationNumber().toString();
	}

	@Override
	public void deserialize(String serial) {
		// TODO Auto-generated method stub
	}

}
