import java.util.Arrays;

public class Sequences{
    public static int[] extractSequence(int[][] seq, int[] pos, int[] dir){
        /* Takes the given 2d array, with a position and direction given as an array with a length of 2, and returns
        a list, with the integers at the position of the line */


        //parsing the arguments into usable variables
        int x = pos[0];
        int y = pos[1];
        int dx = dir[0];
        int dy = dir[1];
        
        // Set the array giving out the result a max length of 40, because i'm too lazy to make it dynamic
        int[] result = new int[40];
        
        // variables to handle the loop
        int i = 0;
        boolean bln = true;
        
        // tries a loop, until the it reaches the exception out of bounds
        while( bln == true ){
            try{
                result[i] = seq[x][y];
                x = x + dx;
                y = y + dy;
                i++;
            } catch(ArrayIndexOutOfBoundsException exception) {
                bln = false;
            }
        }

        // Cleans up the array
        result = Arrays.copyOf(result, i);
        return result;
    }
    public static int maxRun(int[] arr, int max){
        int maxrun = 0;
        boolean bln = false;
        int count = 0;
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == max){
                if (bln == true){
                    count++;
                } else if (bln == false){
                    count = 1;
                    bln = true;
                }
                if (maxrun < count){
                    maxrun = count;
                }
            } else {
                count = 0;
                bln = false;
            }

        }
    return maxrun;
    }
    public static void main(String[] args){
        int[][] kek = {
            { 1, 2, 3, 4, 5, 6, 7, 8},
            { 1, 2, 3, 4, 5, 6, 7, 8},
            { 1, 2, 3, 4, 5, 6, 7, 8},
            { 1, 2, 3, 4, 5, 6, 7, 8},
            { 1, 2, 3, 4, 5, 6, 7, 8},
            { 1, 2, 3, 4, 5, 6, 7, 8},
            { 1, 2, 3, 4, 5, 6, 7, 8},
            { 1, 2, 3, 4, 5, 6, 7, 8} 
        };
        
        int[] pos;
        int[] dir;
        pos = new int[] { 1,1};
        dir = new int[] { 1,1};

        int[] ok = extractSequence(kek, pos, dir);
        System.out.println(Arrays.toString(ok));
        int[] kek2 = {1, 1, 1, 1, 1, 1, 2, 1, 1};
        //System.out.println(maxRun(kek2, 1));
    }
        
}
