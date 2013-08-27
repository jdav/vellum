package com.malleamus.vellum;

import java.util.ArrayList;

/**
 * This class represents an arbitratily large number. Its size is theroretically
 * limited only by the size of the memory allocated to the JVM, not by any 
 * numerical representation scheme, bus size, etc.
 * 
 * This comment is just to test git.
 * 
 * @author jdav
 *
 */
public class ALN {
	
	boolean negative = false;
	ArrayList<Integer> left = new ArrayList<Integer>();
	ArrayList<Integer> right = new ArrayList<Integer>();

	/** 
	 * creates an ALN initialized to zero.
	 */
	public ALN() {
		left.add(0);
	}
	
	/**
	 * creates an ALN initialized to the number represented in the input string
	 * @param str
	 */
	public ALN(String str) {
		
	}
	
	public ALN(Short shortInteger) {
		this(shortInteger.toString());
	}
	
	public ALN(Integer integer) {
		this(integer.toString());
	}
	
	public ALN(Long longInteger) {
		this(longInteger.toString());
	}
	
	public ALN(Float floatingPoint) {
		this(floatingPoint.toString());
	}
	
	public ALN(Double doubleLongInteger) {
		this(doubleLongInteger.toString());
	}
	
	public void increment() {
		
	}
}
