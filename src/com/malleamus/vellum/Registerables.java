package com.malleamus.vellum;

import java.util.ArrayList;

public class Registerables extends ArrayList<Registerable> {

	private static final long serialVersionUID = 3663057797122688987L;

	public String toString() {
		String retVal = "";
		boolean first = true;
		for (Registerable r : this) {
			if (!first) {
				retVal += '~';
			} else {
				first = false;
			}
			retVal += r.getRegistrationNumber();
		}
		return retVal;
	}

}
