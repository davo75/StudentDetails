/*
 The TextFile class is responsible for text file management. i.e. opening, reading
 writing and closing a text file.
 
 @author David Pyle 041110777
 @version 1.0
 @since 11/4/2016
  
 Methods:
    + Scanner openFile(String path)
    + void readFile()
    + void writeFile(String path, ArrayList<Student> content, String headers)
    + void closeFile()

Classes this class requires    
    java.io.BufferedWriter;
    java.io.File;
    java.io.FileNotFoundException;
    java.io.FileWriter;
    java.io.IOException;
    java.util.ArrayList;
    java.util.NoSuchElementException;
    java.util.Scanner;
    java.util.logging.Level;
    java.util.logging.Logger;

 */
package assignment3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TextFile {
    
    //instance variables    
    //file input
    private Scanner input;
    //filewriter for writing data to text file
    private FileWriter fileWriter;
    
    /**
     * Opens a text file
     * @param path location of text file to open
     * @return input stream to file
     */
    public Scanner openFile(String path) {        
        try {             
            input = new Scanner( new File(path));            
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error opening file");
            System.out.println(fileNotFoundException.getMessage());
            System.exit(1);
        }     
        
        return input;
    }
    
    /**
     * Reads the content of a text file
     */
    public void readFile() {
        
        try {
            while (input.hasNext()) {
                System.out.println(input.next());
            }
        }
        catch (NoSuchElementException elementException) {
            System.err.println("File not formatted correctly");
            input.close();
            System.exit(1);
        }
        catch (IllegalStateException stateException) {
            System.err.println("Error reading from file");
            System.exit(1);
        }
    }
    /**
     * Writes content to a text file
     * 
     * @param path  location of destination file
     * @param content	content to write
     * @param headers	content headers to write
     */
    public void writeFile(String path, ArrayList<Student> content, String headers) {
        
        try {
            fileWriter = new FileWriter(path);
       
            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            //write out the list
            bufferedWriter.write(headers);
            content.forEach(student -> {
                try {
                    bufferedWriter.write(student.toString());
                } catch (IOException ex) {
                    Logger.getLogger(TextFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            });      

            // Always close files.
            bufferedWriter.close();
        
        }
        catch (IOException ex) {
            System.err.println("Error writing to file");      
        }       
    }
    
    /**
     * Closes the input stream to a file
     */
    public void closeFile() {
        
        if(input != null) {
            input.close();
        }        
    }
}

