import java.util.Arrays;

public class Sequences{
    public static int[] extractSequence(int[][] seq, int[] pos, int[] dir){
        int x = pos[0];
        int y = pos[1];
        int dx = dir[0];
        int dy = dir[1];
        int dist = 20;
        int[][] rSequence = new int[dist][2];
        int tmp;
        int[][] arrtmp;
        int[] result = new int[dist];

        for (int i = 0; i < dist; i++){
            tmp = x+i*dx;
            rSequence[i][0] = tmp;
            tmp = y+i*dy;
            rSequence[i][1] = tmp;
        }
        int i = 1;
        System.out.println(i);
        System.out.println(seq[2].length);
        boolean bln = true;

        while( bln == true ){
            result[i-1] = seq[x][y];
            x = rSequence[i][0];
            y = rSequence[i][1];
            i++;
            if (x < seq[2].length || y < seq.length){

            } else {
                bln = false;
            }
        }
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
            { 1, 2, 3},
            { 1, 2, 3},
            { 1, 2, 3}
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
