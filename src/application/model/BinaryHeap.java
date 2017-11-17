package application.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Scanner;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class BinaryHeap {
    /** Binary Heap implementation **/

    private static final int d = 2;
    private int heapSize;
    private int[] heap;

    public BinaryHeap(int size){
        heapSize = 0;
        heap = new int[size + 1];
        Arrays.fill(heap,-1);
    }

    public boolean isEmpty(){
        return heapSize == 0;
    }

    public boolean isFull(){
        return heapSize == heap.length;
    }
}
