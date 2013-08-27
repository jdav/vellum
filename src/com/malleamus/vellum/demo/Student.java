package com.malleamus.vellum.demo;

import com.malleamus.vellum.Persisting;
import com.malleamus.vellum.Persistings;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.VellumException;

public class Student implements Registerable {
	
	private RegistrationNumber sn = RegistrationNumber.UNREGISTERED;
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public RegistrationNumber getRegistrationNumber() {
		return sn;
	}

	@Override
	public void setState(Persistings props) throws VellumException {
		Persisting peName = props.getByFieldName("name");
		this.name = (String) peName.getValue();
		this.sn = peName.getRegistrationNumber();
	}

	@Override
	public Persistings getState() {
		Persistings props = new Persistings(sn);
		props.add(new Persisting(sn, getObjectType(), 
                                 "name", name));
		return props;
	}

	@Override
	public String getObjectType() {
		return "STUDENT";
	}

	@Override
	public void setRegistrationNumber(RegistrationNumber rn) {
		this.sn = rn;
	}

	@Override
	public boolean isValid() {
		return sn != null && name != null && !name.isEmpty();
	}

	public String toString() {
		return "(Registration Number: " + sn +
	           ", Name: " + name + ")";
	}

	@Override
	public boolean hasRegistrationNumber() {
		return sn != null && sn != RegistrationNumber.UNREGISTERED;
	}
	
	public boolean equals(Object obj) {
		Student student = null;
		return (obj != null) &&
			   (obj instanceof Student) &&
			   ((student = (Student) obj).name != null) &&
			   (this.name != null) &&
			   (student.name.equals(this.name)) &&
			   (student.sn != null) &&
			   (this.sn != null) &&
			   (student.sn.equals(this.sn));	   
	}

	public int hashCode() {
		if (hasRegistrationNumber()) {
			return sn.hashCode();
		} else {
			return name.hashCode();
		}
	}
}
