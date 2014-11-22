package com.malleamus.vellum.registry.sample;

import java.util.ArrayList;
import java.util.Properties;

import com.malleamus.vellum.Persisting;
import com.malleamus.vellum.Persistings;
import com.malleamus.vellum.Registerable;
import com.malleamus.vellum.Registerables;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.Utilities;
import com.malleamus.vellum.VellumException;
import com.malleamus.vellum.registry.LocalRegistry;

public class Section implements Registerable {

	private RegistrationNumber rn = RegistrationNumber.UNREGISTERED;
	private String name = null;
	private Course course = null;
	private Professor professor = null;
	private Registerables students = new Registerables();
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}
	
	/**
	 * @return the professor
	 */
	public Professor getProfessor() {
		return professor;
	}

	/**
	 * @param professor the professor to set
	 */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	/**
	 * @return the student
	 */
	public Registerables getStudents() {
		return students;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudents(Registerables students) {
		this.students = students;
	}

	@Override
	public RegistrationNumber getRegistrationNumber() {
		return rn;
	}
	
	@Override
	public void setState(Persistings ps) throws VellumException {
		Persisting name = ps.getByFieldName("name");
		this.name = (String) name.getValue();		
		
		Persisting course = ps.getByFieldName("course");
		RegistrationNumber crn = new RegistrationNumber((String) course.getValue());
		this.course = (Course) LocalRegistry.instance().getCopy(crn);	
		
		Persisting professor = ps.getByFieldName("professor");
		RegistrationNumber prn = new RegistrationNumber((String) professor.getValue());
		this.professor = (Professor) LocalRegistry.instance().getCopy(prn);
		
		Persisting student = ps.getByFieldName("students");	
		String strOfRNs = (String) student.getValue();
		String[] rns = strOfRNs.split("~");
		for (String rn : rns) {
			RegistrationNumber srn = new RegistrationNumber(rn);
			Student student1 = (Student) LocalRegistry.instance().getCopy(srn);
			students.add(student1);
		}

		this.rn = name.getRegistrationNumber();
	}
	
	@Override
	public Persistings getState() {
		Persistings props = new Persistings(rn);
		props.add(new Persisting(rn, getObjectType(), "name", name));
		props.add(new Persisting(rn, getObjectType(), "course", course));
		props.add(new Persisting(rn, getObjectType(), "professor", professor));
		props.add(new Persisting(rn, getObjectType(), "students", students));
		return props;
	}
	
	@Override
	public String getObjectType() {
		return "SECTION";
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
		Section section = null;
		return (obj != null) &&
			   (obj instanceof Section) &&
			   ((section = (Section) obj).name != null) &&
			   Utilities.areEqual(this.name, section.name, false) &&
			   Utilities.areEqual(this.rn, section.rn, false) &&
			   Utilities.areEqual(this.course, section.course, true) &&
			   Utilities.areEqual(this.professor, section.professor, true) &&
			   Utilities.areEqual(this.students, section.students, true);	   
	}
	
	public String toString() {
		String ret = 
			"(Registration Number: " + rn +
	        ", Name: " + name +
			", Course: " + (course != null ? course : "Not set") + 
		    ", Professor: " + (professor != null ? professor : "Not set");
		for (Registerable s : students) {
		    ret += ", Student: " + (s != null ? s : "Not set");
		}
		ret += ")";
		return ret;
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
