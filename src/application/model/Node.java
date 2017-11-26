package application.model;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<Node> children;


    public Node(int row, int col, char type){
        this.row = row;
        this.col = col;
        this.type = type;
        this.children = new ArrayList<>();
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

    public float getfVal(){
        return this.fVal;
    }

    public float getgVal() { return this.gVal; }

    public float gethVal() { return this.hVal; }

    /**
     * Method to find the cost of travel between two nodes.
     * @TODO
     * @param n
     * @return
     */
    public float getCost(Node n){
        char t1 = this.type;
        char t2 = n.getType();
        return 0;
    }

    public ArrayList<Node> getChildren() { return this.children; }

    public void setfVal(float value){
        this.fVal = value;
    }

    public void setgVal(float value){
        this.gVal = value;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }
    public void setChildren(Node[][] graph){
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if( (i != row) && (j != col) ){
                    if(graph[row+i][col+j].getType() != '0')
                        children.add(graph[row+i][col+j]);
                }
            }
        }
    }

    @Override
    public int compareTo(Node n){
        if(this.getfVal() < n.getfVal())
            return -1;
        if(this.getfVal() > n.getfVal())
            return 1;
        return 0;
    }
}