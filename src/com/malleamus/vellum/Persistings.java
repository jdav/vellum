package com.malleamus.vellum;

import java.util.ArrayList;
import java.util.Arrays;

public class Persistings extends ArrayList<Persisting> {

	private static final long serialVersionUID = 1L;
	private RegistrationNumber rn = RegistrationNumber.UNREGISTERED;
	
	public Persistings() {}
	
	public Persistings(String serial) {
		String[] components = serial.split("::");
		this.rn = new RegistrationNumber(components[0]);
		for (int x=1; x<components.length; x++) {
			this.add(new Persisting(components[x]));
		}
	}
	
	public Persistings(RegistrationNumber rn) {
		this.rn = rn;
	}

	public RegistrationNumber getRegistrationNumber() {
		return rn;
	}

	public Persisting getByFieldName(String string) throws VellumException {
		for (Persisting pe : this) {
			if (pe.getFieldName().equals(string)) {
				return pe;
			}
		}
		throw new VellumException("Property entry " + string + " not found.");
	}
	
	public String toString() {
		String ret = this.rn + "\n";
		for (Persisting pe : this) {
			ret += pe + "\n";
		}
		return ret;
	}
	
	public String serialize() {
		String ret = this.rn + "::";
		for (Persisting pe : this) {
			ret += pe + "::";
		}
		return ret;
	}
	
	public int hashCode() {
		int accumulator = 23; //randomly selected
		for (Persisting p : this) {
			accumulator ^= p.hashCode();
		}
		return accumulator;
	}
}
