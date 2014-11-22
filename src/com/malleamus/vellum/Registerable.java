package com.malleamus.vellum;

public interface Registerable {
	
	public boolean hasRegistrationNumber();
	
	/**
	 * When a registerable object is registered with a registry, it receives a 
	 * registration number that is unique within that registry. You must properly 
	 * implement this method in order for the object's registry to manage it.
	 * 
	 * @return String
	 */
	public RegistrationNumber getRegistrationNumber();
	
	/**
	 * When a registerable object is registered with a registry, it receives a 
	 * registration number that is unique within that registry. You must properly 
	 * implement this method in order for the object's registry to manage it.
	 * 
	 * @return void
	 */
	public void setRegistrationNumber(RegistrationNumber rn);
	
	/**
	 * All persistable objects must have a type. You must properly implement 
	 * this method in order for the object's registry to manage it.
	 * 
	 * @return String
	 */
	public String getObjectType();
	
	/**
	 * This method returns true if the object can be registered or updated.
	 * This would typically be used to perform edit checks.
	 * 
	 * @return boolean
	 */
	public boolean isValid();

	/**
	 * This method sets the volatile values of the persistable attributes of 
	 * this object from the corresponding values in the props parameter.
	 * 
	 * @param props
	 * @throws VellumException 
	 */
	public void setState(Persistings props) throws VellumException;
	
	/**
	 * This retrieves the volatile values of the persistable attributes
	 * of this object.
	 *  
	 * @return Properties
	 */
	public Persistings getState();
	
	/*
	 * hashCode() MUST be correctly implemented 
	 * for objects to be registered, read, etc.
	 */
	public int hashCode();
	
	public boolean equals(Object o);
	
	public String serialize();
	
	public void deserialize(String sr);
}
