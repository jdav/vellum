package com.malleamus.vellum;

import java.util.ArrayList;
import java.util.Iterator;


public class RegistrationNumbers extends ArrayList<RegistrationNumber> {

	private static final long serialVersionUID = -7750516351159879204L;
	
	public String toString() {
		String str = "";
		Iterator<RegistrationNumber> it = this.iterator();
		while (it.hasNext()) {
			str += it.next() + "~";
		}
		return str;
	}

}
