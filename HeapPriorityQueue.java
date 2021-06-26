/*
    CSI2110 First Coding Assignment
    Name: Patrick Loranger
    Student Number: 300112374

*/

//
// PLEASE NOTE:
// THIS ENTIRE DOCUMENT IS SIMPLY THE MODIFIED SOLUTION OF THE CODE FROM LAB 4
//
// I USED THE SOLUTION FOR LAB 4 WITHOUT THE KEY AND ENTRY VALUES
//

public class HeapPriorityQueue {

    private int tail;
    private int root;
    private RankingSystem[] minheap;

    public HeapPriorityQueue(int size){
        tail = -1;
        root = size;
        minheap = new RankingSystem[size];
    }

    public int size(){
        return tail + 1;
    }

    public RankingSystem[] getRankings(){
        return minheap;
    }

    public void insert(int employerRank, int s) {
        int heapSize = tail + 1;
        if(heapSize == root){
          System.out.println("Cannot insert, queue is full");
          return;
        }
        RankingSystem rank = new RankingSystem(employerRank, s);
        minheap[heapSize] = rank;
        upHeap(heapSize);
        tail++;
    }

    public int removeMin(){
        if (tail == -1){
            System.out.println("Cannot remove, queue is empty");
            return -1;
        }
        RankingSystem remove =  minheap[0];
        minheap[0] = minheap[tail];
        downHeap(0);
        tail-- ;
        return  remove.getName();
    }

    private void upHeap(int location){
        if (location == 0){
            return;
        }          
        int parent = location / 2;
        if(minheap[parent].getRank() > minheap[location].getRank()){
            swap(location, parent);
            upHeap(parent);
        }
    }

    private void downHeap(int location){
        int left = (location * 2) + 1;
        int right = (location * 2) + 2;

        if (left > tail){
            return;
        }
        if (left == tail){
            if (minheap[location].getRank() > minheap[left].getRank()){
                swap(location, left);
            }
            return;
        }
        int toSwap;
        if (minheap[right].getRank() > minheap[left].getRank()){
            toSwap = left;
        }
        else {
            toSwap = right;
        }
        if (minheap[location].getRank() > minheap[toSwap].getRank()){
            swap(location, toSwap);
            downHeap(toSwap);
        }
    }

    private void swap(int location1, int location2){
        RankingSystem temp = minheap[location1];
        minheap[location1] = minheap[location2];
        minheap[location2] = temp;  
    } 
}
