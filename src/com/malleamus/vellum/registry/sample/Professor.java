package com.malleamus.vellum.registry.sample;

import com.malleamus.vellum.Persisting;
import com.malleamus.vellum.Persistings;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.VellumException;

public class Professor implements Registerable {
	
	public String name;
	public RegistrationNumber sn = RegistrationNumber.UNREGISTERED;
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public RegistrationNumber getRegistrationNumber() {
		return sn;
	}
	
	@Override
	public void setState(Persistings props) throws VellumException {
		Persisting name = props.getByFieldName("name");
		this.name = (String) name.getValue();
		this.sn = name.getRegistrationNumber();
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
		return "PROFESSOR";
	}

	@Override
	public void setRegistrationNumber(RegistrationNumber rn) {
		this.sn = rn;
	}

	@Override
	public boolean isValid() {
		return sn != null && name != null && !name.isEmpty();
	}

	@Override
	public boolean hasRegistrationNumber() {
		return sn != null && sn != RegistrationNumber.UNREGISTERED;
	}
	
	public boolean equals(Object obj) {
		Professor professor = null;
		return (obj != null) &&
			   (obj instanceof Professor) &&
			   ((professor = (Professor) obj).name != null) &&
			   (this.name != null) &&
			   (professor.name.equals(this.name)) &&
			   (professor.sn != null) &&
			   (this.sn != null) &&
			   (professor.sn.equals(this.sn));	   
	}
	
	public String toString() {
		return "(Registration Number: " + sn +
	           ", Name: " + name + ")";
	}

	public int hashCode() {
		if (hasRegistrationNumber()) {
			return sn.hashCode();
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
