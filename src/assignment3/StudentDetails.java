/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author 041110777
 */
public class StudentDetails {
    
    Scanner input;
    ArrayList<Student> students;        
    ArrayList<Student> sortedStudentID; 
    ArrayList<Student> sortedStudentSurname;
    ArrayList<Student> sortedStudentName;
    TextFile app;

    
    public StudentDetails() {
        app = new TextFile();
        //create list for holding student data
        students = new ArrayList<>(); 
        //read the file into the student list
        processData();        
        //print out student list
        //System.out.println("Before Sort");
        //students.forEach(student -> System.out.println(student));
        
        //sort the list based on studentID
        //sortedStudentID = bubbleSortID(students);
        sortedStudentID = bubbleSort(students, Student.StudentIDComparator);
        //print out sorted student list
        //System.out.println("After sort by ID");
        //sortedStudentID.forEach(student -> System.out.println(student));
        //write list to text file
        app.writeFile("sortedStudentID.txt", sortedStudentID, getHeaders());
        
        //sort the list by surname
        sortedStudentSurname = bubbleSort(students, Student.StudentSurnameComparator);
        //print out sorted student list
        //System.out.println("After sort by surname");
        //sortedStudentSurname.forEach(student -> System.out.println(student));
        //write sorted list to text file
        app.writeFile("sortedStudentSurname.txt", sortedStudentSurname, getHeaders());
        
        
        //sort the list by first name
        sortedStudentName = bubbleSort(students, Student.StudentFirstNameComparator);
        //print out sorted student list
        //System.out.println("After sort by name");
        //sortedStudentName.forEach(student -> System.out.println(student));
        //write sorted list to text file
        app.writeFile("sortedStudentName.txt", sortedStudentName, getHeaders());
        
        showMainMenu();
        
    }
    
    private void showMainMenu() {
        
        // flag to see if user has decided to exit the main menu
        boolean userExit = false;
        // Scanner object for capturing user input
        Scanner input = new Scanner(System.in);
        
	String[] mainMenu = {	"\nChoose an option: ", 
				"1. View Student Details",
				"2. Exit\n"
	};
	
        // show the main menu until user exits
        while (!userExit) 
        {
            // error handling in case user enters a string
            try 
            {  
                // display menu
                for (String menuItem : mainMenu) 
                {
                    System.out.println(menuItem);
		}
                
                // get the user's choice
                int choice = input.nextInt();
                 
                // handle the menu choice
                switch (choice) 
                {                
                    case 1: 
                        showSubMenu();                        
                        break;             
                    case 2:
                        //set flag in order to exit the loop
                        userExit = true;
                        System.out.println("Exiting...Bye!");
                        break;
                    default:
                        // display error message in case no option entered
                        System.out.println("Invalid option.");
                        break;
                }
            }
            //handle exception in case invalid input
            catch (InputMismatchException inputMismatchException) 
            {                
                // ignore any input so user can select again from the menu
                input.nextLine();
                //display error message
                System.out.println("Invalid input. Numbers only please");
            }
        }       
        
    }
    
    private void showSubMenu() {
        
         // flag to see if user has decided to exit the main menu
        boolean userExitSub = false;
	boolean invalidInput = false;
        // Scanner object for capturing user input
        Scanner inputSub = new Scanner(System.in);
        
        ArrayList<Student> student = null;
	
	String[] subMenu = {	"Search by: ",
				"1. Student ID",
				"2. Surname",
				"3. First Name",
				"4. Back to main menu\n"
	};
        
        while (!userExitSub) 
        {
            // error handling in case user enters a string
            try {
                // display sub menu
                for (String menuItem : subMenu) 
                {
                    System.out.println(menuItem);
		}

                int subChoice = inputSub.nextInt();
                inputSub.nextLine();
                // only convert non-negative numbers
                switch (subChoice) {
                    case 1:
			invalidInput = false;
                        System.out.print("Enter Student ID: ");
                        String studentID = inputSub.nextLine();
                        student = findStudent(studentID, "ID");                        
                        break;
                    case 2:
			invalidInput = false;
                        System.out.print("Enter surname: ");
                        String surname = inputSub.nextLine();                               
                        student = findStudent(surname, "surname");                        
                        break;
                    case 3:
			invalidInput = false;
                        System.out.print("Enter first name: ");
                        String firstName = inputSub.nextLine();                                
                        student = findStudent(firstName, "first");                        
                        break;
                    case 4:
                        userExitSub = true;			
                        break;    
                    default:
                        // display error message in case no option entered
                        System.out.println("Invalid option.\n");
			invalidInput = true;
                        break;
                }
		if (!userExitSub && !invalidInput) {
		    if (student != null && student.size() > 1) {
			System.out.println("\nMultiple students found");
			int count = 1;
			for (Student stud: student) {
			    System.out.println(count + " " + stud.getStudentID() + " " + stud.getGivenName() + " " + stud.getSurname());
			    count++;
			}
			System.out.println("\nWhich student do you want to view details for?");
			Scanner inputNum = new Scanner(System.in);
			int numChoice = inputNum.nextInt();

			System.out.println(getHeaders());		    
			System.out.println(student.get(numChoice-1));

		    } else if (student != null && student.size() == 1){
			System.out.println("\nStudent found!\n");
			System.out.println(getHeaders());
			System.out.println(student.get(0));
		    } else if (student != null && student.isEmpty()){
			System.out.println("\nStudent not found.\n");
		    }
		}
	    }           	    
            //handle exception in case invalid input
            catch (InputMismatchException inputMismatchException) 
            {                
                // ignore any input so user can select again from the menu
                inputSub.nextLine();
                //display error message
                System.out.println("Invalid input. Numbers only please\n");
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) 
            {                
                // ignore any input so user can select again from the menu
                inputSub.nextLine();
                //display error message
                System.out.println("Invalid input. Choose from the list.");
            }	    
		
        }
    }
    
    private ArrayList<Student> findStudent(String searchTerm, String searchOn ) {            
        
        ArrayList<Student> studentsFound = new ArrayList<>(); 
        
        switch (searchOn) {
            
            case "ID":
                for (Student student: students) {
                    if (student.getStudentID().equalsIgnoreCase(searchTerm)) {                        
                        studentsFound.add(student);
                    }
                }               
                break;
            case "surname":
                for (Student student: students) {
                    if (student.getSurname().equalsIgnoreCase(searchTerm)) {
                        studentsFound.add(student);
                        
                    }
                } 
                break;
            case "first":
                for (Student student: students) {
                    if (student.getGivenName().equalsIgnoreCase(searchTerm)) {
                        studentsFound.add(student);                        
                    }
                } 
                break;          
        }
        return studentsFound;     
    }
    
    private String getHeaders() {
           return ( String.format("%-15s", "Student ID") 
                    + String.format("%-15s", "First Name") 
                    + String.format("%-15s", "Last Name") 
                    + String.format("%-20s", "Preferred Name") 
                    + String.format("%-20s", "Contact Number")
                    + String.format("%-15s", "Full Time")
                    + String.format("%-15s", "Love Java")
                    + "Doing Diploma"
        );
        
    }
    
    private void processData() {
        
        //open and read the file contents
        
        input = app.openFile("studentdata.txt");

        //read the file input
        try {
            while (input.hasNext()) {                
                //add a new student
                addStudent(input.next());               
            }
        }
        //catch any exceptions
        catch (NoSuchElementException elementException) {
            System.err.println("File not formatted correctly");
            input.close();
            System.exit(1);
        }
        catch (IllegalStateException stateException) {
            System.err.println("Error reading from file");
            System.exit(1);
        }      

        app.closeFile();
        
    }
    
    private void addStudent(String studentData) {
        
        //split up the data
        String[] splitData = studentData.split(";");
        
        students.add(new Student(   splitData[0], 
                                    splitData[1], 
                                    splitData[2], 
                                    splitData[3], 
                                    Boolean.parseBoolean(splitData[4]), 
                                    splitData[5], 
                                    Boolean.parseBoolean(splitData[6]),
                                    Student.ContinueToDiploma.valueOf(splitData[7]))
                ); 
    }
    
    public static ArrayList<Student> bubbleSort( ArrayList<Student> students, Comparator<Student> sortby)
    {
        int j;
        boolean flag = true;   // set flag to true to begin first pass
        Student temp;   //holding variable
   
        while (flag) {   
            
            flag= false;    //set flag to false awaiting a possible swap
            
            for( j=0;  j<students.size()-1;  j++ ) {
                if( sortby.compare(students.get(j), students.get(j+1)) > 0 ) {
                //if ((students.get(j).surname).compareTo(students.get(j+1).surname) > 0) {
                    temp = students.get(j);   
                    //swap elements
                    students.set(j,students.get(j+1) );
                    students.set(j+1, temp);                           
                    flag = true;              //shows a swap occurred  
                } 
            } 
        } 
     return students;
    }    
}
