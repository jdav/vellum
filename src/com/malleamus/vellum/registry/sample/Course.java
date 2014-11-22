package com.malleamus.vellum.registry.sample;

import com.malleamus.vellum.Persisting;
import com.malleamus.vellum.Persistings;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.VellumException;

public class Course implements Registerable {
	
	private RegistrationNumber rn = RegistrationNumber.UNREGISTERED;
	private String name = null;

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public RegistrationNumber getRegistrationNumber() {
		return rn;
	}

	@Override
	public void setState(Persistings props) throws VellumException {
		Persisting name = props.getByFieldName("name");
		this.name = (String) name.getValue();
		this.rn = name.getRegistrationNumber();
	}

	@Override
	public Persistings getState() {
		Persistings props = new Persistings(rn);
		props.add(new Persisting(rn, getObjectType(), 
				                 "name", name));
		return props;
	}

	@Override
	public String getObjectType() {
		return "COURSE";
	}

	@Override
	public void setRegistrationNumber(RegistrationNumber rn) {
		this.rn = rn;
	}

	@Override
	public boolean isValid() {
		return rn != null && name != null && !name.isEmpty();
	}

	@Override
	public boolean hasRegistrationNumber() {
		return rn != null && rn != RegistrationNumber.UNREGISTERED;
	}
	
	public boolean equals(Object obj) {
		Course course = null;
		return (obj != null) &&
			   (obj instanceof Course) &&
			   ((course = (Course) obj).name != null) &&
			   (this.name != null) &&
			   (course.name.equals(this.name)) &&
			   (course.rn != null) &&
			   (this.rn != null) &&
			   (course.rn.equals(this.rn));	   
	}
	
	public String toString() {
		return "(Registration Number: " + rn +
	           ", Name: " + name + ")";
	}

	public int hashCode() {
		if (hasRegistrationNumber()) {
			return rn.hashCode();
		} else {
			return name.hashCode();
		}
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deserialize(String sr) {
		// TODO Auto-generated method stub
		
	}
}
