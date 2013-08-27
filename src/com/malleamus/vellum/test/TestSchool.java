package com.malleamus.vellum.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.malleamus.vellum.Registerables;
import com.malleamus.vellum.RegistrationNumber;
import com.malleamus.vellum.RegistrationNumbers;
import com.malleamus.vellum.demo.Course;
import com.malleamus.vellum.demo.Professor;
import com.malleamus.vellum.demo.SchoolRegistry;
import com.malleamus.vellum.demo.Section;
import com.malleamus.vellum.demo.Student;

public class TestSchool {

	@Test
	public void registerStoreAndReload() {
		try {
			//Create the registry
			SchoolRegistry r = SchoolRegistry.instance();
			r.load();
			r.clear();
			r.store();
			
			//Add some objects...
			Student student1 = new Student();
			student1.setName("John Smith");
			RegistrationNumber srn = r.register(student1);
		
			Course course1 = new Course();
			course1.setName("English 101");
			RegistrationNumber crn = r.register(course1);
			
			Professor professor1 = new Professor();
			professor1.setName("Frumious Bandersnatch");
			RegistrationNumber prn = r.register(professor1);

			//Store the objects...
			r.store();
			
			//Create another registry
			SchoolRegistry s = SchoolRegistry.instance();
			
			//load in the persistent data
			s.load();
			
			//Try to read in the three objects
			//you created above
			Student student2 = (Student) s.getCopy(srn);
			Course course2 = (Course) s.getCopy(crn);
			Professor professor2 = (Professor) s.getCopy(prn);
	
			assertNotNull(student2);
			assertNotNull(course2);
			assertNotNull(professor2);
			
			assertEquals(student1, student2);
			assertEquals(course1, course2);
			assertEquals(professor1, professor2);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void addAndReadStudent() {
		try {
			//Setup
			SchoolRegistry r = SchoolRegistry.instance();
			r.load();
			r.clear();
			r.store();
			
			Student student1 = new Student();
			student1.setName("John Smith");
			RegistrationNumber rn = r.register(student1);
			Student student2 = (Student) r.getCopy(rn);
			
			//Validate
			assertEquals(student1, student2);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void modifyStudent() {
		try {
			SchoolRegistry r = SchoolRegistry.instance();
			r.load();
			r.clear();
			r.store();
			
			//Create a student and register it...
			Student student1 = new Student();
			student1.setName("John Smith");
			RegistrationNumber rn = r.register(student1);

			//Check out this student, modify 
			//it, and check it in again...
			Student student2 = (Student) r.checkOut(rn);
			student2.setName("Jeff Jones");
			r.checkIn(student2);
			
			//Read this student again...
			Student student3 = (Student) r.getCopy(rn);
			
			//Make sure they are equal...
			assertEquals(student2, student3);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void addAndReadSection() {
		try {
			SchoolRegistry r = SchoolRegistry.instance();
			r.load();
			r.clear();
			r.store();
			
			Course course = new Course();
			course.setName("English 101");
			r.register(course);
			
			Professor professor = new Professor();
			professor.setName("Frumious Bandersnatch");
			r.register(professor);
			
			Student student1 = new Student();
			student1.setName("Vladimir Putin");
			r.register(student1);
			
			Student student2 = new Student();
			student2.setName("Bonnie Bedelia");
			r.register(student2);
			
			Student student3 = new Student();
			student3.setName("Chad Michaels");
			r.register(student3);
			
			Section section = new Section();
			section.setName("001");
			section.setCourse(course);
			section.setProfessor(professor);
			Registerables students = new Registerables();
			students.add(student1);
			students.add(student2);
			students.add(student3);
			section.setStudents(students);
			RegistrationNumber sectionRN = 
					r.register(section);
			//System.out.println(section);
			
			r.store();
			
			//Create another registry
			SchoolRegistry s = SchoolRegistry.instance();
			
			//load in the persistent data
			s.load();
			
			//Try to read in the section 
			//object you created above
			Section section2 = (Section) s.getCopy(sectionRN);
	
			assertNotNull(section2);
			assertNotNull(section2.getCourse());
			assertNotNull(section2.getProfessor());
			assertNotNull(section2.getStudents());
			assertEquals(section2.getStudents().size(), 3);
			
			assertTrue(section2.getStudents().contains(student1));
			assertTrue(section2.getStudents().contains(student2));		
			assertTrue(section2.getStudents().contains(student3));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void modifySection() {
		try {
			SchoolRegistry r = SchoolRegistry.instance();
			r.load();
			r.clear();
			r.store();
			
			//Create a Section and register it...
			Section section1 = new Section();
			section1.setName("John Smith");
			Course course1 = new Course();
			course1.setName("zsazsa");
			r.register(course1);
			r.store();
			section1.setCourse(course1);
			Professor professor = new Professor();
			professor.setName("eva");
			r.register(professor);
			r.store();
			section1.setProfessor(professor);
			Student student1 = new Student();
			student1.setName("magda");
			r.register(student1);
			r.store();
			Registerables students = new Registerables();
			students.add(student1);
			Student student2 = new Student();
			student2.setName("jenny");
			r.register(student2);
			r.store();
			students.add(student2);
			section1.setStudents(students);
			System.out.println("Section1 = " + section1);
			RegistrationNumber rn = r.register(section1);
			System.out.println("Registered section1 = " + section1);
			r.store();

			//Check out this Section, modify 
			//it, and check it in again...
			Section section2 = (Section) r.checkOut(rn);
			section2.setName("Jeff Jones");
			Course course2 = new Course();
			course2.setName("hilton");
			r.register(course2);
			section2.setCourse(course2);
			Professor professor2 = new Professor();
			professor2.setName("gentrea");
			r.register(professor2);
			section2.setProfessor(professor2);
			section2.getStudents().remove(student1);
			r.checkIn(section2);
			
			//Read this section again...
			Section section3 = (Section) r.getCopy(rn);
			
			//Make sure they are equal...
			assertEquals(section3, section2);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void find() {
		try {
			//Create the registry
			SchoolRegistry r = SchoolRegistry.instance();
			r.load();
			r.clear();
			r.store();
			
			//Add some objects...
			Student student1 = new Student();
			student1.setName("John Smith");
			RegistrationNumber srn = r.register(student1);
			
			//Try to find them...
			boolean found = false;
			RegistrationNumbers srns = r.find("STUDENT", "name", "John Smith");
			for (RegistrationNumber rn : srns) {
				Student student2 = (Student) r.getCopy(rn);
				if (student2.equals(student1)) {
					found = true;
					break;
				}
			}
			assertTrue(found);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}

