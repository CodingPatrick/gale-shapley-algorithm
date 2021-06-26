public class Q3P2 {
 
    public int getIndex(int[] arr, int value) {
        int k=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]==value){
                k=i;
                break;
            }
        }
        return k;
    } 

    /*
    for w in women:
        for m in [men w would prefer over current_partner(w)]:
            if m prefers w to current_partner(m) 
                return false
    return true
    
    */

    public boolean stableMatchChecker(int[] students, int[] employers, int[][] A, int[][] B){

        for( int i = 0; i <= employers.length-1; i++){
            int number = employers[i]+1;
            int number1 = students[i]+1;
            
            for( int j = 0; j <= students.length-1; j++){
                if( getIndex(B[i], number) > getIndex(B[j], number) ){
                    if( getIndex(A[i], number1) > getIndex(A[j], number1) ){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args){
/*        // true
        int[] employers = new int[]{ 0, 2, 1 };
        int[][] B = new int[][]{ {1, 2, 3}, {1, 2, 3}, {1, 2, 3} }; //employers[students]

        int[] students = new int[]{ 0, 2, 1 };
        int[][] A = new int[][]{ {1, 2, 3}, {1, 3, 2}, {3, 1, 2} }; //students[employers]
*/
        // false
        int[] students = new int[]{ 0, 1, 2 };
        int[] employers = new int[]{ 0, 1, 2 };
        int[][] A = new int[][]{ {1, 2, 3}, {1, 3, 2}, {3, 1, 2} }; //students[employers]
        int[][] B = new int[][]{ {1, 2, 3}, {1, 2, 3}, {1, 2, 3} }; //employers[students]

        Q3P2 checker = new Q3P2();
        System.out.println(checker.stableMatchChecker(students, employers, A, B));
    }

}