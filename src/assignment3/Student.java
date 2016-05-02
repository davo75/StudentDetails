/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.util.Comparator;

/**
 *
 * @author 041110777
 */
public class Student {
    
    private String surname;
    private String givenName;
    private String preferredName;
    private String studentID;
    private boolean fullTime;
    private String contactNum;
    private boolean loveJava;
    private ContinueToDiploma toDiploma;

    public String getSurname() {
	return surname;
    }

    public String getGivenName() {
	return givenName;
    }

    public String getStudentID() {
	return studentID;
    }
    
    public enum ContinueToDiploma {
        PENDING, CONFIRMED, FALSE 
    }
            
    public Student(String surname, String givenName, String preferredName, String studentID, boolean fullTime, String contactNum, boolean loveJava, ContinueToDiploma toDiploma) {
        this.surname = surname;
        this.givenName = givenName;
        this.preferredName = preferredName;
        this.studentID = studentID;
        this.fullTime = fullTime;
        this.contactNum = contactNum;
        this.loveJava = loveJava;
        this.toDiploma = toDiploma;
    }
    
    @Override
    public String toString() {
        return  String.format("%-15s", studentID)
                + String.format("%-15s", givenName) 
                + String.format("%-15s", surname) 
                + String.format("%-20s", preferredName) 
                + String.format("%-20s", contactNum)
                + String.format("%-15s", fullTime).toUpperCase()
                + String.format("%-15s", loveJava).toUpperCase()
                + toDiploma
                + "\n";    
    }
    
    public static Comparator<Student> StudentSurnameComparator 
		      = new Comparator<Student>() {
	@Override
	public int compare(Student s1, Student s2) {
	    String studentName1 = s1.getSurname();
	    String studentName2 = s2.getSurname();

	    //ascending order
	    return studentName1.compareTo(studentName2);
	}
    };
        
    public static Comparator<Student> StudentFirstNameComparator 
		      = new Comparator<Student>() {
	@Override
	public int compare(Student s1, Student s2) {
	    String studentName1 = s1.getGivenName();
	    String studentName2 = s2.getGivenName();

	    //ascending order
	    return studentName1.compareTo(studentName2);
	}
    };
        
    public static Comparator<Student> StudentIDComparator 
		      = new Comparator<Student>() {
	@Override
	public int compare(Student s1, Student s2) {
	    String studentID1 = s1.getStudentID();
	    String studentID2 = s2.getStudentID();

	    //ascending order
	    return studentID1.compareTo(studentID2);
	}
    };    
}
