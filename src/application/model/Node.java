package application.model;

/**
 * Node class, the main comparison class used for the A* algorithms.
 * Implements the Comparable interface.
 * @author Andrew Wang
 * @author Mauricio Alvarez
 */
public class Node implements Comparable<Node>{

    private int row, col;
    private float gVal;
    private float fVal;
    private float hVal;
    private char type;
    private Node parent;


    public Node(int row, int col, char type){
        this.row = row;
        this.col = col;
        this.type = type;
        this.parent = null;
        this.gVal = Float.MAX_VALUE;
    }

    public int getRow(){
        return this.row;
    }
    public int getCol(){
        return this.col;
    }

    public char getType(){
        return this.type;
    }

    public float getF(){
        return this.fVal;
    }

    @Override
    public int compareTo(Node n){
        if(this.getF() < n.getF())
            return -1;
        if(this.getF() > n.getF())
            return 1;
        return 0;
    }
}