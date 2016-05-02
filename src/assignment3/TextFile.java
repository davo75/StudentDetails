/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    private Scanner input;
    private FileWriter fileWriter;
    
    public Scanner openFile(String path) {
        
        try {         
            //File file = new File(".");
            //for(String fileNames : file.list()) System.out.println(fileNames);
            input = new Scanner( new File(path));            
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error opening file");
            System.out.println(fileNotFoundException.getMessage());
            System.exit(1);
        }     
        
        return input;
    }
    
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
    
    public void closeFile() {
        
        if(input != null) {
            input.close();
        }        
    }
}

