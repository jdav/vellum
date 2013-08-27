package com.malleamus.vellum;

public class Utilities {

	public static boolean areEqual(Object first, Object second, boolean equalEvenIfBothNull) {
		return (equalEvenIfBothNull ? 
				areEqualEvenIfBothNull(first, second) :
				areEqual(first, second));	   
	}
	
	private static boolean areEqual(Object first, Object second) {
		return (first != null && second != null &&
				first.equals(second));
	}
	
	private static boolean areEqualEvenIfBothNull(Object first, Object second) {
		return ((first == null && second == null) ||
				(first != null && second != null &&
				 first.equals(second)));
	}
}
