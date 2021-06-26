/*
    CSI2110 First Coding Assignment
    Name: Patrick Loranger
    Student Number: 300112374

*/

// This is my Ranking System class to help with the priority queue and some sorting
public class RankingSystem {
    
    // declaring the variables
    private int studentName;
    private int studentRanking;
    
    // constructor which takes in the rankings first, and student second
    public RankingSystem(int ranking, int student ) {
        studentName = student;
        studentRanking = ranking;
    }
    
    // simply returns the integer attached to the name of the student in question
    int getName() {
        return studentName;
    }
    
    // returns the rank of the student in question
    int getRank() {
        return studentRanking;
    }
}

