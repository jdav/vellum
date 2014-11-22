package com.malleamus.vellum.registry.distributed.request;

import java.util.HashMap;
import java.util.Map;

import com.malleamus.diener.Request;
import com.malleamus.diener.RequestFactory;
import com.malleamus.vellum.Persistings;
import com.malleamus.vellum.RegistrationNumber;

public class VellumRequestFactory implements RequestFactory {
	
	public Map<String, Class> map = 
			new HashMap<String, Class>();
	
	public VellumRequestFactory() {
		map.put("clear", ClearRequest.class);
		map.put("load", LoadRequest.class);
		map.put("store", StoreRequest.class);
		map.put("register", RegisterRequest.class);
		map.put("getcopy", GetCopyRequest.class);
		map.put("checkout", CheckoutRequest.class);
		map.put("checkin", CheckinRequest.class);
		map.put("ischeckedout", IsCheckedOutRequest.class);
		map.put("ischeckedin", IsCheckedInRequest.class);
		map.put("isavalidcheckout", IsAValidCheckoutRequest.class);
		map.put("withdraw", WithdrawRequest.class);
		map.put("find", FindRequest.class);
	}

	@Override
	public Request create(String rawInput) throws Exception {
		String[] components = rawInput.split("|");
		Class cls = map.get(components[0]);
		if (cls != null) {
			Request request = (Request) cls.newInstance();
			request.deserialize(rawInput);
			return request;
		} else {
			return new InvalidRequest();
		}
	}

}
