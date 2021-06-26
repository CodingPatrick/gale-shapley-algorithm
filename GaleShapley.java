/*
    CSI2110 First Coding Assignment
    Name: Patrick Loranger
    Student Number: 300112374

*/

/*
    Here are a couple links that helped me with the manipulations used in my code.
    I say where I got inspired by them in the comments along my code.

    Link #1 : https://stackoverflow.com/questions/6881458/converting-a-string-array-into-an-int-array-in-java/26164167
    Link #2 : https://stackoverflow.com/questions/8777217/remove-all-zeros-from-array

*/

// imports needed for this chunk of code
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// beginning of the class GaleShapley
class GaleShapley {

    // declaring my instance variables, the 5 first ones being the variables required in the assignment.
    Stack<Integer> Sue;
    public int[] students;
    public int[] employers;
    public int[][] A;
    public HeapPriorityQueue PQ[];
    public String[] employerName;
    public String[] studentName;

    // start of the initialize method
    public void initialize(String filename) {
        // this function will get a text file as an input

        // declaring a new Buffered Reader
        BufferedReader reader;
        try{
            // making a variable to create a new Buffered Reader tha will start reading the input file
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            // making the value of n the integer found in the first line of the text file
            int n = Integer.valueOf(line);

            // declaring my data structures required in the initialize function
            Sue = new Stack<Integer>();
            students = new int[n];
            employers = new int[n];
            A = new int[n][n];
            employerName = new String[n];
            studentName = new String[n];

            // pushing all the employers into the stack
            for(int i = 0; i < n; i++){
                line = reader.readLine();
                Sue.push(i);
                // adding each employer name to a new array
                employerName[i] = line;
            }

            // initializing all the values in the arrays to -1
            for(int i = 0; i < n; i++){
                line = reader.readLine();
                students[i] = -1;
                employers[i] = -1;
                // adding each student name to a new array
                studentName[i] = line;
            }

            // creating a 2D array for student rankings
            String matrix = new String("");
            for(int i = 0; i < n; i++){
                line = reader.readLine();
                matrix = matrix + line + " ";
            }
            reader.close();
            String[] newArray = matrix.split("[\\s\\,]+");
            int[] temp;
            temp = new int[newArray.length];
            
            // converting my split String array into an int array
            // this code was inspired by Link #1, found in the header of my document
            for(int i = 1; i<newArray.length; i = i + 2){
                temp[i] = Integer.parseInt(newArray[i]);
            }
            
            // once I create my temp array, I am found with a new array of the same size, but without the employers
            // they are all replaced by zeros. I need to remove those 0's to get a proper array of just studentRanks.
            // this code was inspired by Link #2, found in the header of my document
            int newIndex = 0;
            for(int i = 0; i < temp.length; i++){
                if(temp[i] != 0){
                    temp[newIndex++] = temp[i];
                }
            }
            // creating a copy of the array, with a new size
            int[] studentRanks = new int[newIndex];
            System.arraycopy(temp, 0, studentRanks, 0, newIndex);

            // I now transform my 1D array into a 2D arrya
            int tracker = 0;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    A[j][i] = studentRanks[tracker++];
                }
            }

            // creating a 2D array for employer rankings
            // these next lines of code are the same as above, just for the employerRanks
            int[] temp2;
            temp2 = new int[newArray.length];

            for(int i = 0; i<newArray.length; i = i + 2){
                temp2[i] = Integer.parseInt(newArray[i]);
            }

            int newIndex2 = 0;
            for(int i = 0; i < temp2.length; i++){
                if(temp2[i] != 0){
                    temp2[newIndex2++] = temp2[i];
                }
            }
            int[] employerRanks = new int[newIndex2];
            System.arraycopy(temp2, 0, employerRanks, 0, newIndex2);

            int[][] employerRanks2D;
            employerRanks2D = new int[n][n];
            
            int tracker2 = 0;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    employerRanks2D[i][j] = employerRanks[tracker2++];
                }
            }

            // initializing the priority queue
            PQ = new HeapPriorityQueue[n];

            // creating new priority queues, following the code found in the assignment PDF
            for(int i = 0; i < n; i++){
                PQ[i] = new HeapPriorityQueue(n);
                for(int j = 0; j < n; j++){
                    PQ[i].insert(employerRanks2D[i][j], j);
                }
            }

        // making sure the file given is found, or has a proper name
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void execute(){
        // this function will execute the GaleShapley algorithm
        // this is converted PSEUDOCODE from the assignemnt pages
        
        // run this function as long as the stack Sue is not empty
        while( !Sue.empty() ){
            int e = Sue.pop();
            int s = PQ[e].removeMin();
            int eNext = students[s];

            // checking if the student has a pair yet
            if ( students[s] == -1){
                students[s] = e;
                employers[e] = s;
            }

            // checking the students preferences, if he prefers one employer to another
            else if (A[s][e] < A[s][eNext]){
                students[s] = e;
                employers[e] = s;
                employers[eNext] = -1;
                Sue.push(eNext);
            }
            else {
                Sue.push(e);
            }

        }
    }

    public void save(String filename) {
        // this function will create a new file for the ouput for run()
        
        // creating a new Buffered Reader
        BufferedReader reader;
        try{
            // initializing everything I need to create a new file
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            int n = Integer.valueOf(line);
            FileWriter filewriter = new FileWriter("matches_" + filename);
            BufferedWriter matchesFile = new BufferedWriter(filewriter);
            // this for loop writes a new line n times, with the proper matches for each student and employer.
            for(int i = 0; i < n; i ++){
                int j = employers[i];
                matchesFile.write("Match " + i + ": " + employerName[i] + " - " + studentName[j]);
                matchesFile.newLine();
            }
            // close the file and give the output
            matchesFile.close();
            System.out.println("The file has been succesfully created, please check the folder for the new file.");

        // print a line stating the file was not created in reason of an error. 
        // it should never get to this point.
        } catch (IOException e) {
            System.out.println("Oops, something went wrong, please try again. :/");
        }
    }

    public static void main(String[] args){
        // this main will call all of the functions necessary to complete the program
        
        // asking user for the name of the file they want to open
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the name of the file you would like to open: ");
        String filename = scanner.nextLine();
        scanner.close();

        // running the 3 methods of the class GaleShapley with the user text file input
        GaleShapley galeshapley = new GaleShapley();
        galeshapley.initialize(filename);
        galeshapley.execute();
        galeshapley.save(filename);
    }
}