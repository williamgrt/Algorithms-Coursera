import java.util.Iterator;

/**
 * Created by grt on 2017/6/19.
 * Deque: A double-ended queue
 */

public class Deque<Item> implements Iterable<Item> {
    //nested class to define Node
    private class Node{
        private Item data;
        private Node next;
        private Node prev;
    }

    //the size of deque
    //the first and last node of deque
    private int N;
    private Node first, last;

    //construct an empty deque
    public Deque(){
        N = 0;
        first = null;
        last = null;
    }
    //is the deque empty?
    public boolean isEmpty(){
        return N == 0;
    }
    //return the number of items on the deque
    public int size(){
        return N;
    }
    //add the item to the front
    public void addFirst(Item item){
        if (item == null){
            throw new java.lang.NullPointerException();
        }
        Node oldFirst = first;
        first = new Node();
        first.data = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty()){
            last = first;
        }else{
            oldFirst.prev = first;
        }
        N++;
    }
    //add the item to the last
    public void addLast(Item item){
        if (item == null){
            throw new java.lang.NullPointerException();
        }
        Node oldLast = last;
        last = new Node();
        last.data = item;
        last.prev = oldLast;
        last.next = null;
        if (isEmpty()){
            first = last;
        }else{
            oldLast.next = last;
        }
        N++;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        Item item = first.data;
        Node oldFirst = first;
        first = first.next;
        oldFirst.next = null;
        if (first == null){
            last = null;
        }else {
            first.prev = null;
        }
        N--;
        return item;
    }
    // remove and return the item from the end
    public Item removeLast(){
        if (isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        Item item = last.data;
        Node oldLast = last;
        last = last.prev;
        oldLast.prev = null;
        if (last == null){
            first = null;
        }else {
            last.next = null;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{
        private Node current = first;

        public boolean hasNext(){
            return current != null;
        }
        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next(){
            if (!hasNext()){
                throw new java.util.NoSuchElementException();
            }
            Item item = current.data;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args){
        Deque<Integer> test = new Deque<>();
        System.out.println(test.isEmpty());
        test.addFirst(2);
        test.addFirst(4);
        test.addLast(3);
        System.out.println(test.size());
        Iterator<Integer> iterator = test.iterator();
        while (iterator.hasNext()){
            int i = iterator.next();
            System.out.println(i);
        }
        int j = test.removeLast();
        int k = test.removeFirst();
        System.out.println(j + " " + k);
        iterator = test.iterator();
        while (iterator.hasNext()){
            int i = iterator.next();
            System.out.println(i);
        }
    }
}
