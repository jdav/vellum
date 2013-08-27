package com.malleamus.vellum;

import java.util.Arrays;
import java.util.Random;

public class RegistrationNumber {

	private final static int MIN_LENGTH = 10;
	private final static int MAX_LENGTH = 25;
	private static enum ProvenanceType { ASSIGNED, GENERATED, UNKNOWN };
	
	/*
	private final static char[] CHARACTERS = 
		{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
		 'R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h',
		 'i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y',
		 'z','0','1','2','3','4','5','6','7','8','9','!','@','#','$','%','^',
		 '&','*','(',')','-','_','=','+','?','<','>',',','.','~',':',';','|'};
	*/
	
	private final static char[] CHARACTERS = 
		{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
		 'R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7',
		 '8','9'};
	
	public static RegistrationNumber UNREGISTERED =
			new RegistrationNumber("UNREGISTERED");

	private char[] sn = new char[1];
	private ProvenanceType provenance = ProvenanceType.UNKNOWN;
	
	public RegistrationNumber(boolean generateIt) {
		if (generateIt) {
			generate();
		}
	}
	
	public RegistrationNumber(String str) {
		sn = str.toCharArray();
	}
	
	public boolean isSet() {
		return sn != null;
	}
	
	public boolean wasAssigned() {
		return provenance == ProvenanceType.ASSIGNED;
	}
	
	public boolean wasGenerated() {
		return provenance == ProvenanceType.GENERATED;
	}
	
	public void generate() {
		int x = MAX_LENGTH - MIN_LENGTH;
		int length = MIN_LENGTH;
		
		if (x > 0) {
			length += (new Random()).nextInt(x);
		}
		
		sn = new char[length];
		Random randomChar = new Random();
		for (int idx=0; idx<length; idx++) {
			sn[idx] = CHARACTERS[randomChar.nextInt(CHARACTERS.length)];
		}
	}
	
	public String toString() {
		return new String(sn);
	}
	
	public boolean equals(Object obj) {
		System.out.print((obj != null ? obj.toString() : "NULL") + " = " + this.toString() + " ? ");
		boolean result = (obj != null && 
				obj instanceof RegistrationNumber && 
				obj.toString().equals(this.toString()));
		System.out.println(result);
		return result;
	}
	
	public int hashCode() {
		return Arrays.hashCode(sn);
	}
}
