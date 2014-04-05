package com.malleamus.vellum.test;

import org.junit.Test;

import com.malleamus.vellum.RegistrationNumber;

public class TestRegistrationNumber {

	@Test
	public void test() {
		for (int x=0; x<10; x++) {
			RegistrationNumber serialNumber = new RegistrationNumber(false);
			serialNumber.generate();
			System.out.println(serialNumber);
		}
	}

}
