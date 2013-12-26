package com.malleamus.vellum.demo;

import java.io.File;

import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.LocalRegistry;

public class SchoolRegistry extends LocalRegistry {
	
	private static SchoolRegistry instance = null;
	
	public static SchoolRegistry instance() throws VellumException {
		return (instance == null ? (instance = new SchoolRegistry()) : instance);
	}
	
	private SchoolRegistry() throws VellumException {
		//Get Registry from persistent storage
		super(new File("C:\\\\Users\\jdav\\schoolregistry.txt"), new SchoolFactory());
		try {
			load();
		} catch (Exception e) {
			throw new VellumException(e);
		}
		buildIndices();
	}
}
