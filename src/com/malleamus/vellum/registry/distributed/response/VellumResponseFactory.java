package com.malleamus.vellum.registry.distributed.response;

import java.util.HashMap;
import java.util.Map;

import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.diener.ResponseFactory;
import com.malleamus.vellum.registry.distributed.request.CheckinRequest;
import com.malleamus.vellum.registry.distributed.request.CheckoutRequest;
import com.malleamus.vellum.registry.distributed.request.ClearRequest;
import com.malleamus.vellum.registry.distributed.request.FindRequest;
import com.malleamus.vellum.registry.distributed.request.GetCopyRequest;
import com.malleamus.vellum.registry.distributed.request.InvalidRequest;
import com.malleamus.vellum.registry.distributed.request.IsAValidCheckoutRequest;
import com.malleamus.vellum.registry.distributed.request.IsCheckedInRequest;
import com.malleamus.vellum.registry.distributed.request.IsCheckedOutRequest;
import com.malleamus.vellum.registry.distributed.request.LoadRequest;
import com.malleamus.vellum.registry.distributed.request.RegisterRequest;
import com.malleamus.vellum.registry.distributed.request.StoreRequest;
import com.malleamus.vellum.registry.distributed.request.WithdrawRequest;

public class VellumResponseFactory implements ResponseFactory {
	
	public Map<String, Class> map = new HashMap<String, Class>();
	
	public VellumResponseFactory() {
		map.put("success", SuccessResponse.class);
		map.put("error", ErrorResponse.class);
		map.put("boolean", BooleanResponse.class);
		map.put("registerable", RegisterableResponse.class);
	}

	@Override
	public Response create(String rawInput) throws Exception {
		String[] components = rawInput.split("|");
		Class cls = map.get(components[0]);
		if (cls != null) {
			Response response = (Response) cls.newInstance();
			response.deserialize(rawInput);
			return response;
		} else {
			return new InvalidResonse();
		}
	}

}
