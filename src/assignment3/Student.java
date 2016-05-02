/*
 The Student class stores information for a student.
 
 @author David Pyle 041110777
 @version 1.0
 @since 11/4/2016
  
 Methods:
    + String toString()
    + Comparator<Student> StudentSurnameComparator 
    + Comparator<Student> StudentFirstNameComparator 
    + Comparator<Student> StudentIDComparator 
    + String getSurname()
    + String getGivenName()
    + String getStudentID()

Classes this class requires    
    java.util.Comparator;

 */
package assignment3;

import java.util.Comparator;

public class Student {
    
    private String surname;
    private String givenName;
    private String preferredName;
    private String studentID;
    private boolean fullTime;
    private String contactNum;
    private boolean loveJava;
    private ContinueToDiploma toDiploma;

    public enum ContinueToDiploma {
        PENDING, CONFIRMED, FALSE 
    }
    
    /**
     * Constructor sets the student information
     * 
     * @param surname student's surname
     * @param givenName student's first name
     * @param preferredName student's preferred name
     * @param studentID student's id
     * @param fullTime true if student is full time, false if not
     * @param contactNum student's contact number
     * @param loveJava true if student loves Java, false if not
     * @param toDiploma true if student continuing to diploma, false if not
     */
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
    
    /**
     * Displays a student's information
     * 
     * @return student information
     */
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
    
    /**
     * Compares two student surnames
     */
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
    
    /**
     * Compares two student first names
     */
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
    
    /**
     * Compares two student ids
     */
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
    
    /**
     * Gets a student's surname
     * 
     * @return student's surname 
     */
    public String getSurname() {
	return surname;
    }
    
    /**
     * Gets a student's first name
     * 
     * @return student's first name 
     */
    public String getGivenName() {
	return givenName;
    }
    
    /**
     * Gets a student's ID
     * 
     * @return student ID 
     */
    public String getStudentID() {
	return studentID;
    }
}
