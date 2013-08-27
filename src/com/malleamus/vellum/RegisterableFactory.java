package com.malleamus.vellum;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public abstract class RegisterableFactory {

	private Map<String, Class> classMap = 
		new Hashtable<String, Class>();
	
	public RegisterableFactory() throws VellumException {
		try {
			build();
		} catch (VellumException ve) {
			throw ve;
		} catch (Exception e) {
			throw new VellumException(e);
		}
	}
	
	protected abstract void build() throws VellumException;

	protected void register(Class cls) throws VellumException {
		try {
			Registerable p = (Registerable) cls.newInstance();
			classMap.put(p.getObjectType(), cls);		
		} catch (Exception e) {
			throw new VellumException(e);
		}
	}
	
	public Iterator<Class> getClasses() {
		Collection<Class> clss = classMap.values();
		return clss.iterator();
	}

	public Registerable create(Persistings pes) throws VellumException {
		try {
			if (pes == null) {
				throw new VellumException("No properties found for object");
			}
			
			if (pes.isEmpty()) {
				throw new VellumException("No properties found for object: " + pes.getRegistrationNumber());
			}

			//Find the class in the property entries
			Persisting pe = pes.get(0);
			Class theClass = classMap.get(pe.getObjectType());
			
			//Make sure the class is acceptable
			if (theClass == null) {
				throw new VellumException("Unrecognized class type");
			}
			
			//Create a new instance of that class
			Registerable t = (Registerable) theClass.newInstance();
			
			//Add the values to the instantiated object and return it
			t.setState(pes);
			return t;
		} catch (VellumException ve) {
			ve.printStackTrace();
			throw ve;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VellumException(e);
		}
	}
}
