import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by grt on 2017/6/19.
 */
public class Permutation {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        String[] input = In.readStrings(args[1]);
        for (String i: input){
            queue.enqueue(i);
        }
        for (int i = 0; i < k; i++){
            StdOut.println(queue.sample());
        }
    }
}
