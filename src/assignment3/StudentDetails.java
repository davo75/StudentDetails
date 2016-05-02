/*
 The StudentDetails is the main class for managing the application. It loads the
 student data from the text file, sorts the data and writes the sorted data back
 to a text file(s). It also displays a menu for interacting with the student data.
 
 @author David Pyle 041110777
 @version 1.0
 @since 11/4/2016
  
 Methods:
    - void showMainMenu()
    - void showSubMenu() 
    - ArrayList<Student> findStudent(String searchTerm, String searchOn )
    - void readData()
    - void addStudent(String studentData)
    - ArrayList<Student> bubbleSort( ArrayList<Student> students, Comparator<Student> sortby)

Classes this class requires    
    java.util.ArrayList;
    java.util.Comparator;
    java.util.InputMismatchException;
    java.util.NoSuchElementException;
    java.util.Scanner;

 */

package assignment3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class StudentDetails {
    
    //user input
    Scanner input;
    //list of students
    ArrayList<Student> students;        
    //list for students sorted by student ID
    ArrayList<Student> sortedStudentID; 
    //list for students sorted by surname
    ArrayList<Student> sortedStudentSurname;
    //list for students sorted by first name
    ArrayList<Student> sortedStudentName;
    //class for reading and writing text files
    TextFile app;
    
    //name of student data text file
    final static String STUDENT_FILE = "studentdata.txt";
    //name of student data text file to write sorted list by ID
    final static String SORTED_BY_ID = "sortedStudentID.txt";
    //name of student data text file to write sorted list by surname
    final static String SORTED_BY_SURNAME = "sortedStudentSurname.txt";
    //name of student data text file to write sorted list by first name
    final static String SORTED_BY_FIRSTNAME = "sortedStudentName.txt";

    /**
     * Constructor reads the data, writes the sorted lists to text files and
     * displays menus to the user
     */
    public StudentDetails() {
        app = new TextFile();
        //create list for holding student data
        students = new ArrayList<>(); 
	
        //read the file into the student list
        readData();                 
	
        //sort the list based on studentID
        sortedStudentID = bubbleSort(students, Student.StudentIDComparator);       
        //write list to text file
        app.writeFile(SORTED_BY_ID, sortedStudentID, getHeaders());
        
        //sort the list by surname
        sortedStudentSurname = bubbleSort(students, Student.StudentSurnameComparator);       
        //write sorted list to text file
        app.writeFile(SORTED_BY_SURNAME, sortedStudentSurname, getHeaders());        
        
        //sort the list by first name
        sortedStudentName = bubbleSort(students, Student.StudentFirstNameComparator);       
        //write sorted list to text file
        app.writeFile(SORTED_BY_FIRSTNAME, sortedStudentName, getHeaders());
        
	//show main menu
        showMainMenu();        
    }
    
    /**
     * Displays the main menu
     */
    private void showMainMenu() {
        
        // flag to see if user has decided to exit the main menu
        boolean userExit = false;
        // Scanner object for capturing user input
        Scanner input = new Scanner(System.in);
        
	//menu items
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
			//show sub menu
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
    
    /**
     * Displays sub menu
     */
    private void showSubMenu() {
        
        // flag to see if user has decided to exit the main menu
        boolean userExitSub = false;
	//flag any invalid input
	boolean invalidInput = false;
        // Scanner object for capturing user input
        Scanner inputSub = new Scanner(System.in);
        
	//student list for holding search results in case of multiple hits
        ArrayList<Student> student = null;
	
	//sub menu items
	String[] subMenu = {	"Search by: ",
				"1. Student ID",
				"2. Surname",
				"3. First Name",
				"4. Back to main menu\n"
	};
        
	//loop until user exits sub menu
        while (!userExitSub) 
        {
            // error handling in case user enters a string
            try {
                // display sub menu
                for (String menuItem : subMenu) 
                {
                    System.out.println(menuItem);
		}
		//get the user's choice
                int subChoice = inputSub.nextInt();
                inputSub.nextLine();
                
                switch (subChoice) {
                    case 1:
			//clear error flag
			invalidInput = false;
			//get id
                        System.out.print("Enter Student ID: ");
                        String studentID = inputSub.nextLine();
			//find student searching by ID
                        student = findStudent(studentID, "ID");                        
                        break;
                    case 2:
			//clear error flag
			invalidInput = false;
			//get surname
                        System.out.print("Enter surname: ");
                        String surname = inputSub.nextLine();  
			//find student searching by surname
                        student = findStudent(surname, "surname");                        
                        break;
                    case 3:
			//clear error flag
			invalidInput = false;
			//get first name
                        System.out.print("Enter first name: ");
                        String firstName = inputSub.nextLine();   
			//find student by first name
                        student = findStudent(firstName, "first");                        
                        break;
                    case 4:
			//user exits
                        userExitSub = true;			
                        break;    
                    default:
                        // display error message in case no option entered
                        System.out.println("Invalid option.\n");
			invalidInput = true;
                        break;
                }
		
		//if the user has exited the sub menu and there is no invalid input then display the search results
		if (!userExitSub && !invalidInput) {
		    //if there are multiple search results
		    if (student != null && student.size() > 1) {
			System.out.println("\nMultiple students found");
			//display the multiple results
			int count = 1;
			for (Student stud: student) {
			    System.out.println(count + " " + stud.getStudentID() + " " + stud.getGivenName() + " " + stud.getSurname());
			    count++;
			}
			//ask user which student they want to see details for
			System.out.println("\nWhich student do you want to view details for?");
			Scanner inputNum = new Scanner(System.in);
			int numChoice = inputNum.nextInt();
			//display the details for the student selected by the user
			System.out.println(getHeaders());		    
			System.out.println(student.get(numChoice-1));
		    //show the search result
		    } else if (student != null && student.size() == 1){
			System.out.println("\nStudent found!\n");
			System.out.println(getHeaders());
			System.out.println(student.get(0));
		    //student not found so show message	
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
    
    /**
     * Searches for a student by student ID, first name or surname
     * 
     * @param searchTerm string to search for
     * @param searchOn field to search on
     * @return list of students matching search criteria
     */
    private ArrayList<Student> findStudent(String searchTerm, String searchOn) {            
        
	//list of search results
        ArrayList<Student> studentsFound = new ArrayList<>(); 
        
        switch (searchOn) {
            //search by student ID
            case "ID":
                for (Student student: students) {
		    //add the student if a match found
                    if (student.getStudentID().equalsIgnoreCase(searchTerm)) {                        
                        studentsFound.add(student);
                    }
                }               
                break;
	    //search by student surname
            case "surname":
                for (Student student: students) {
		    //add the student if a match found
                    if (student.getSurname().equalsIgnoreCase(searchTerm)) {
                        studentsFound.add(student);
                        
                    }
                } 
                break;
	    //search by student first name
            case "first":
                for (Student student: students) {
		    //add the student if a match found
                    if (student.getGivenName().equalsIgnoreCase(searchTerm)) {
                        studentsFound.add(student);                        
                    }
                } 
                break;          
        }
	//return the search results
        return studentsFound;     
    }
    
    /**
     * Display student detail headers
     * 
     * @return headers for student details
     */
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
    
    /**
     * Adds student data to a list from a text file
     */
    private void readData() {
        
        //open and read the file contents        
        input = app.openFile(STUDENT_FILE);

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
	//close the file
        app.closeFile();
        
    }
    
    /**
     * Adds a student to the student list
     * 
     * @param studentData the student data to add
     */
    private void addStudent(String studentData) {
        
        //split up the data from the text file
        String[] splitData = studentData.split(";");
        
	//add the data to the student list
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
    
    /**
     * Sorts a student list using a bubble Sort algorithm
     * 
     * @param students the list to sort
     * @param sortby the field to sort the data (ID, first name or surname)
     * @return the sorted list
     */
    public static ArrayList<Student> bubbleSort( ArrayList<Student> students, Comparator<Student> sortby)
    {
	//index for loop
        int studentCounter;
	//set flag to true to begin first pass
        boolean flag = true;   
	//holding variable
        Student temp;   
	
	
        while (flag) {   
            //set flag to false awaiting a possible swap
            flag= false;    
            
	    //loop through the list swapping if element before is greater then the preceeding one
            for( studentCounter=0;  studentCounter<students.size()-1;  studentCounter++ ) {
		//use the sortBy comparator as basis for comparison
                if( sortby.compare(students.get(studentCounter), students.get(studentCounter+1)) > 0 ) {   
		    //temporarily store a student during the swap
                    temp = students.get(studentCounter);   
                    //swap elements
                    students.set(studentCounter,students.get(studentCounter+1) );
                    students.set(studentCounter+1, temp);    
		    //indicate a swap occurred  
                    flag = true;              
                } 
            } 
        } 
	//return the sorted list
	return students;
    }    
}
