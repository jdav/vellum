package com.malleamus.vellum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Registry {

	public Registry(File file, RegisterableFactory factory) {
		super();
		this.file = file;
		this.factory = factory;
	}

	public void load(InputStream is) throws IOException {
		Properties props = new Properties();
		props.load(is);
		fromProperties(props);
	}
	
	public void clear() {
		peMap.clear();
		checkOuts.clear();
	}

	public void store(OutputStream os, String string) throws IOException {
		Properties props = toProperties();
		props.store(os, string);
	}

	/**
	 * Load data from file
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 */
	public void load() throws IllegalArgumentException, IllegalAccessException,
			ClassNotFoundException, InstantiationException {
		try {
			if (file.exists()) {
				synchronized (this) {
					FileInputStream fis = new FileInputStream(file);
					load(fis);
					fis.close();
				}

			} else {
				synchronized (this) {
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					store(fos, "");
					fos.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Store data to file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void store() throws FileNotFoundException, IOException {
		synchronized (this) {
			FileOutputStream fos = new FileOutputStream(file);
			store(fos, "");
			fos.close();
		}
	}

	public void buildIndices() {
		// Iterate through all of the property entries
		for (Persistings persistings : peMap.values()) {
			for (Persisting persisting : persistings) {
				index(persisting);
			}
		}
	}

	/**
	 * Add the values in the input Registerable object to the registry's data.
	 * NOTE: UPDATES REGISTERABLE'S REGISTRATION NUMBER
	 * @param t
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public RegistrationNumber register(Registerable t) throws VellumException {
		try {
			if (t.isValid() && !t.hasRegistrationNumber()) {
				RegistrationNumber rn = 
						new RegistrationNumber(true);
				t.setRegistrationNumber(rn);
				Persistings pes = t.getState();
				peMap.put(pes.getRegistrationNumber(), pes);
				index(pes);
				return rn;
			} else {
				throw new VellumException(
						"Invalid Data: Object cannot be registered or updated");
			}
		} catch (VellumException ve) {
			throw ve;
		} catch (Exception e) {
			throw new VellumException(e);
		}
	}

	/**
	 * Build a single object associated with a specific registration number from
	 * the contents of the data
	 * 
	 * @param props
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Registerable getCopy(RegistrationNumber rn)
			throws VellumException {

		try {
			// Find all of the properties associated
			// with this registration number
			Persistings e = peMap.get(rn);
			
			if (e == null) {
				throw new VellumException("No record of object " + rn);
			}

			if (e.isEmpty()) {
				throw new VellumException("No properties found for object " + rn);
			}

			// Then create an object based on those properties and return it
			return (Registerable) factory.create(e);
		} catch (VellumException ve) {
			throw ve;
		} catch (Exception e) {
			throw new VellumException(e);
		}
	}

	public Registerable checkOut(RegistrationNumber rn) throws VellumException {
		if (isCheckedOut(rn)) {
			throw new VellumException("Registerable Object "
					+ rn + " is already checked out.");
		} else {
			Registerable r = getCopy(rn);			
			synchronized (checkOuts) {
				checkOuts.put(rn, r);
			}
			return r;
		}
	}
	
	/**
	 * Update the registry's data with the values in the input registerable
	 * object that have changed since it was last checked in.
	 * 
	 * @param t
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void checkIn(Registerable t) throws VellumException {
		try {
			if (t.isValid() && isAValidCheckOut(t)) {
				// Update the sets and maps of
				// property entries held in the registry
				Persistings currentState = 
						peMap.get(t.getRegistrationNumber());
				
				// For each property...
				for (Persisting pe : currentState) {
					//find the corresponding index...
					Index index = indexes.get(pe.getIndexName());
					if (index != null) {
						//and remove the current property 
						index.remove(t);
					}
				}

				//Now update the current state to the
				//state of the checked-in object
				currentState.clear();
				currentState.addAll(t.getState());

				// For each property...
				for (Persisting pe : currentState) {
					//find the corresponding index...
					Index index = indexes.get(pe.getIndexName());
					if (index != null) {
						// and add the new property
						index.put(t);
					}
				}

				// check this object in so others can check it out again                                                           c
				checkOuts.remove(t.getRegistrationNumber());
			} else {
				throw new VellumException(
						"Invalid Data: Object cannot be checked in");
			}
		} catch (VellumException ve) {
			throw ve;
		} catch (Exception e) {
			throw new VellumException(e);
		}
	}
	
	public boolean isCheckedOut(RegistrationNumber rn) {
		return checkOuts.containsKey(rn);
	}
	
	public boolean isCheckedIn(RegistrationNumber rn) {
		return !checkOuts.containsKey(rn);
	}
	
	public boolean isAValidCheckOut(Registerable t) {
		Registerable t2 = 
				checkOuts.get(t.getRegistrationNumber());
		return t2 != null && t2 == t;
	}

	/**
	 * Delete the input Registerable object from the Registry's data
	 * 
	 * @param t
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void withdraw(Registerable t) throws IllegalArgumentException,
			IllegalAccessException {

		// Find all of the property values...
		Persistings pes = t.getState();

		// For each property...
		for (Persisting pe : pes) {
			// Remove this property entry from the registry per se...
			peMap.remove(pe.getRegistrationNumber());

			// ...and remove this changed object from the corresponding indices
			RegistrationNumbers t2 = find(pe.getObjectType(), pe.getFieldName(), pe.getValue());
			if (t2.contains(t)) {
				t2.remove(t);
			}
			
			//Remove this object from check outs if present
			checkOuts.remove(t.getRegistrationNumber());
		}

		// Make sure object doesn't linger in list of check outs
		checkOuts.remove(t.getRegistrationNumber());
	}

	public RegistrationNumbers find(String objectType, String fieldName, String value) {
		Index idx = indexes.get(Index.getIndexName(objectType, fieldName));
		return idx.get(value);
	}

	public RegistrationNumbers find(Persisting entry) {
		return find(entry.getObjectType(), entry.getFieldName(), entry.getValue());
	}
	
	/******************************************************************
	 * NON-PUBLIC REALM
	 ******************************************************************/
	protected File file = null; // Should be set in constructor
	protected RegisterableFactory factory = null; // Should be set in
												  // constructor

	private static final long serialVersionUID = -2804882784191950300L;

	/**
	 * Unique set of property entries
	 */
	//private Set<Persisting> peSet = new HashSet<Persisting>();

	/**
	 * This map allows all of the property entries associated with a particular
	 * registration number to be retrieved.
	 */
	private Map<RegistrationNumber, Persistings> peMap = 
				new Hashtable<RegistrationNumber, Persistings>();

	/**
	 * This map allows you to find an index of registerable objects based on the
	 * name of an attribute.
	 */
	private Map<String, Index> indexes = new Hashtable<String, Index>();
	
	/**
	 * This map allows you to manage checkouts.
	 */
	private Map<RegistrationNumber, Registerable> checkOuts = 
			new Hashtable<RegistrationNumber, Registerable>();

	private Properties toProperties() {
		Properties props = new Properties();
		for (Persistings persistings : peMap.values()) {
			for (Persisting persisting : persistings) {
				props.put(persisting.getName(), 
						  persisting.getValue().toString());
			}
		}
		//System.out.println(props);
		return props;
	}

	private void fromProperties(Properties props) {
		Enumeration<?> e = props.keys();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = (String) props.get(name);
			Persisting pe = new Persisting(name, value);
			RegistrationNumber rn = pe.getRegistrationNumber();
			Persistings pes = peMap.get(rn);
			if (pes == null) {
				pes = new Persistings(rn);
				peMap.put(rn, pes);
			}
			pes.add(pe);
		}
	}
	
	protected void index(Persistings entries) {
		for (Persisting entry : entries) {
			index(entry);
		}
	}

	protected void index(Persisting entry) {
		// Find the index that matches the index name...
		Index index = indexes.get(Index.getIndexName(entry));

		// Add this entry to that index...
		if (index != null) {
			RegistrationNumbers snl = index.get(entry.getValue());
			snl.add(entry.getRegistrationNumber());
		}
	}
}