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
    private List<Node> children;


    public Node(int row, int col, char type){
        this.row = row;
        this.col = col;
        this.type = type;
        this.parent = null;
        this.gVal = Float.MAX_VALUE;
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

    public float getF(){
        return this.fVal;
    }

    public float getC(Node n){
        char t1 = this.type;
        char t2 = n.getType();
        return 0;
    }

    public void getChildren(Node[][] graph){
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
        if(this.getF() < n.getF())
            return -1;
        if(this.getF() > n.getF())
            return 1;
        return 0;
    }
}