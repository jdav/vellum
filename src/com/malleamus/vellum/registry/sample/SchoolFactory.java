package com.malleamus.vellum.registry.sample;

import com.malleamus.vellum.*;

public class SchoolFactory extends RegisterableFactory {

	private static final long serialVersionUID = 9083057829141426960L;
	
	public SchoolFactory() throws VellumException {
		super();
	}

	@Override
	protected void build() throws VellumException {
		this.register(Course.class);
		this.register(Student.class);
		this.register(Professor.class);
		this.register(Section.class);
	}

}
