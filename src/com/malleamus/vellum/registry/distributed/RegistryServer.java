package com.malleamus.vellum.registry.distributed;

import java.io.File;
import java.io.IOException;

import com.malleamus.vellum.RegisterableFactory;
import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.LocalRegistry;
import com.malleamus.vellum.registry.distributed.request.VellumRequestFactory;

public class RegistryServer extends com.malleamus.diener.Server {
	
	/**
	 * These are some comments necessary to force a git update...
	 * @param args
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws VellumException
	 */
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, VellumException {
		File file = new File(args[0]); //Where is the stuff stored?
		RegisterableFactory registerableFactory = //How do we interpret it?
				(RegisterableFactory) Class.forName(args[1]).newInstance();
		int port = Integer.parseInt(args[2]); //How can people reach me?
		RegistryServer server = new RegistryServer(file, registerableFactory, port);
		server.startUp();
	}

	public RegistryServer(File file, RegisterableFactory factory, int port) throws VellumException {
		super(new VellumRequestFactory(), port);
		LocalRegistry.instance(file, factory);
	}
}
