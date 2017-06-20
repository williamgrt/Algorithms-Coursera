import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by grt on 2017/6/19.
 * RandomizedQueue:  A randomized queue is similar to a stack or queue,
 * except that the item removed is chosen uniformly at random from items in the data structure.
 *
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] deque;
    private int N;           //deque size;
    private int arraySize;   //array size;
    //resize the array
    private void resize(int size){
        Item[] newDeque = (Item[])new Object[size];
        for (int i = 0; i < arraySize; i++){
            newDeque[i] = deque[i];
        }
        deque = newDeque;
        arraySize = size;
    }
    //swap two items in the array
    private void swap(int a, int b){
        Item temp = deque[a];
        deque[a] = deque[b];
        deque[b] = temp;
    }

    //construct an empty randomized queue
    public RandomizedQueue(){
        N = 0;
        arraySize = 1;
        deque = (Item[])new Object[arraySize];
    }
    //is the queue empty?
    public boolean isEmpty(){
        return N == 0;
    }
    //return the number of items on the queue
    public int size(){
        return N;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null){
            throw new java.lang.NullPointerException();
        }
        int location = N++;
        deque[location] = item;
        if (N > arraySize / 2) {
            resize(2 * arraySize);
        }
    }
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        int randomLocation = StdRandom.uniform(N);
        swap(--N, randomLocation);
        Item item = deque[N];
        if (N > 0 && N < arraySize / 4){
            resize(arraySize / 2);
        }
        return item;
    }
    // return (but do not remove) a random item
    public Item sample(){
        if (isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        int randomLocation = StdRandom.uniform(N);
        return deque[randomLocation];
    }
    //return an independent iterator over items in random order
   public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
   }

   private class RandomizedQueueIterator implements Iterator<Item>{
       private int location = N;
       private Item[] ownDeque = deque;

       public RandomizedQueueIterator(){
           StdRandom.shuffle(ownDeque);
       }
       public boolean hasNext(){
           return N > 0;
       }
       public void remove(){
           throw new java.lang.UnsupportedOperationException();
       }
       public Item next(){
           if (!hasNext()){
               throw new java.util.NoSuchElementException();
           }
           Item item = ownDeque[--location];
           return item;
       }

    }

    public static void main(String[] args){

    }
}
