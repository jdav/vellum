package com.malleamus.vellum.registry.distributed;

import java.net.URI;

import com.malleamus.diener.Client;
import com.malleamus.diener.Request;
import com.malleamus.diener.Response;
import com.malleamus.diener.ResponseFactory;
import com.malleamus.vellum.Persisting;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.RegistrationNumbers;
import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.Registry;
import com.malleamus.vellum.registry.distributed.request.BuildIndicesRequest;
import com.malleamus.vellum.registry.distributed.request.CheckinRequest;
import com.malleamus.vellum.registry.distributed.request.CheckoutRequest;
import com.malleamus.vellum.registry.distributed.request.ClearRequest;
import com.malleamus.vellum.registry.distributed.request.GetCopyRequest;
import com.malleamus.vellum.registry.distributed.request.IsAValidCheckoutRequest;
import com.malleamus.vellum.registry.distributed.request.IsCheckedInRequest;
import com.malleamus.vellum.registry.distributed.request.IsCheckedOutRequest;
import com.malleamus.vellum.registry.distributed.request.LoadRequest;
import com.malleamus.vellum.registry.distributed.request.RegisterRequest;
import com.malleamus.vellum.registry.distributed.request.StoreRequest;
import com.malleamus.vellum.registry.distributed.request.WithdrawRequest;
import com.malleamus.vellum.registry.distributed.response.BooleanResponse;
import com.malleamus.vellum.registry.distributed.response.RegisterableResponse;
import com.malleamus.vellum.registry.distributed.response.SuccessResponse;
import com.malleamus.vellum.registry.distributed.response.VellumResponseFactory;

public class RemoteRegistry implements Registry {
	
	private URI uri;
	private ResponseFactory factory = new VellumResponseFactory();

	public RemoteRegistry(URI uri) {
		super();
		this.uri = uri;
	}

	@Override
	public void clear() throws VellumException {
		voidRequest(new ClearRequest());	
	}

	@Override
	public void load() throws VellumException {
		voidRequest(new LoadRequest());	
	}

	@Override
	public void store() throws VellumException {
		voidRequest(new StoreRequest());	
	}

	@Override
	public void buildIndices() throws VellumException {
		voidRequest(new BuildIndicesRequest());
	}

	@Override
	public RegistrationNumber register(Registerable t) throws VellumException {
		try {
			Client client = new Client(factory, uri.getHost(), uri.getPort());
			RegisterRequest request = new RegisterRequest(t);
			Response response = client.transact(request);
			if (response instanceof RegisterableResponse) {
				RegisterableResponse rr = (RegisterableResponse) response;
				Registerable r = rr.getRegisterable();
				return r.getRegistrationNumber();
			} else {
				throw new VellumException(response.toString());
			}
		} catch (VellumException e) {
			throw e;
		} catch (Exception e) {
			throw new VellumException(e);
		}
	}

	@Override
	public Registerable getCopy(RegistrationNumber rn) throws VellumException {
		return registerableRequest(new GetCopyRequest(rn));
	}

	@Override
	public Registerable checkOut(RegistrationNumber rn) throws VellumException {
		return registerableRequest(new CheckoutRequest(rn));
	}

	@Override
	public void checkIn(Registerable t) throws VellumException {
		voidRequest(new CheckinRequest(t));
	}

	@Override
	public boolean isCheckedOut(RegistrationNumber rn) throws VellumException {
		return booleanRequest(new IsCheckedOutRequest(rn));
	}

	@Override
	public boolean isCheckedIn(RegistrationNumber rn) throws VellumException {
		return booleanRequest(new IsCheckedInRequest(rn));
	}

	@Override
	public boolean isAValidCheckOut(Registerable t) throws VellumException {
		return booleanRequest(new IsAValidCheckoutRequest(t));
	}

	@Override
	public void withdraw(Registerable t) throws VellumException {
		voidRequest(new WithdrawRequest(t));
	}

	@Override
	public RegistrationNumbers find(String objectType, String fieldName,
			String value) throws VellumException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistrationNumbers find(Persisting entry) throws VellumException {
		// TODO Auto-generated method stub
		return null;
	}

	
	private void voidRequest(Request request) throws VellumException {
		try {
			Client client = new Client(factory, uri.getHost(), uri.getPort());
			Response response = client.transact(request);
			if (!(response instanceof SuccessResponse)) {
				throw new VellumException(response.toString());
			}
		} catch (VellumException e) {
			throw e;
		} catch (Exception e) {
			throw new VellumException(e);
		}
	}
	
	private boolean booleanRequest(Request request) throws VellumException {
		try {
			Client client = new Client(factory, uri.getHost(), uri.getPort());
			Response response = client.transact(request);
			if (response instanceof RegisterableResponse) {
				BooleanResponse r = (BooleanResponse) response;
				return r.getBoolean();
			} else {
				throw new VellumException(response.toString());
			}
		} catch (VellumException e) {
			throw e;
		} catch (Exception e) {
			throw new VellumException(e);
		}
	}
	
	private Registerable registerableRequest(Request request) throws VellumException {
		try {
			Client client = new Client(factory, uri.getHost(), uri.getPort());
			Response response = client.transact(request);
			if (response instanceof RegisterableResponse) {
				RegisterableResponse rr = (RegisterableResponse) response;
				return rr.getRegisterable();
			} else {
				throw new VellumException(response.toString());
			}
		} catch (VellumException e) {
			throw e;
		} catch (Exception e) {
			throw new VellumException(e);
		}
	}
}
