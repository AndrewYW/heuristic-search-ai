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

    public Node getParent(){ return this.parent; }

    /**
     * Method to find the cost of travel between two nodes.
     * @TODO
     * @param n
     * @return
     */
    public float getCost(Node n){
        char t1 = this.type;
        char t2 = n.getType();
        int row1 = this.row;
        int col1 = this.col;
        int row2 = n.getRow();
        int col2 = n.getCol();
        boolean straight;

        if(row1 == row2 || col1 == col2)
            straight = true;
        else
            straight = false;

        if(straight){
            switch (t1){
                case '1':
                    switch (t2){
                        case '1':
                            return 1;
                        case '2':
                            return (float)1.5;
                        case 'a':
                            return 1;
                        case 'b':
                            return (float)1.5;
                    }
                    break;
                case '2':
                    switch (t2){
                        case '1':
                            return (float)1.5;
                        case '2':
                            return 2;
                        case 'a':
                            return (float)1.5;
                        case 'b':
                            return 2;
                    }
                    break;
                case 'a':
                    switch (t2){
                        case '1':
                            return 1;
                        case '2':
                            return (float)1.5;
                        case 'a':
                            return (float).25;
                        case 'b':
                            return (float).375;
                    }
                    break;
                case 'b':
                    switch (t2){
                        case '1':
                            return (float)1.5;
                        case '2':
                            return 2;
                        case 'a':
                            return (float).375;
                        case 'b':
                            return (float).25;
                    }
                    break;
            }
        } else{
            switch(t1){
                case '1':
                    switch (t2){
                        case 'a':
                        case '1':
                            return (float)Math.sqrt(2);
                        case '2':
                        case 'b':
                            return (float)((Math.sqrt(2)+Math.sqrt(8))/2);
                    }
                    break;
                case '2':
                    switch (t2){
                        case 'a':
                        case '1':
                            return (float)((Math.sqrt(2)+Math.sqrt(8))/2);
                        case '2':
                        case 'b':
                            return (float)Math.sqrt(8);
                    }
                    break;
                case 'a':
                    switch (t2){
                        case 'a':
                        case '1':
                            return (float)Math.sqrt(2);
                        case '2':
                        case 'b':
                            return (float)((Math.sqrt(2)+Math.sqrt(8))/2);
                    }
                    break;
                case 'b':
                    switch (t2){
                        case 'a':
                        case '1':
                            return (float)((Math.sqrt(2)+Math.sqrt(8))/2);
                        case '2':
                        case 'b':
                            return (float)Math.sqrt(8);
                    }
                    break;
            }
        }

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
        if(inBounds(row+1, col+1)) {
            if (graph[row + 1][col + 1].getType() != '0')
                children.add(graph[row + 1][col + 1]);
        }
        if(inBounds(row, col+1)){
            if (graph[row][col + 1].getType() != '0')
                children.add(graph[row][col + 1]);
        }
        if(inBounds(row-1, col+1)) {
            if (graph[row - 1][col + 1].getType() != '0')
                children.add(graph[row - 1][col + 1]);
        }
        if(inBounds(row+1, col)) {
            if (graph[row + 1][col].getType() != '0')
                children.add(graph[row + 1][col]);
        }
        if(inBounds(row-1, col)) {
            if (graph[row - 1][col].getType() != '0')
                children.add(graph[row - 1][col]);
        }
        if(inBounds(row+1, col-1)) {
            if (graph[row + 1][col - 1].getType() != '0')
                children.add(graph[row + 1][col - 1]);
        }
        if(inBounds(row, col-1)) {
            if (graph[row][col - 1].getType() != '0')
                children.add(graph[row][col - 1]);
        }
        if(inBounds(row-1, col-1)) {
            if (graph[row - 1][col - 1].getType() != '0')
                children.add(graph[row - 1][col - 1]);
        }
    }

    private boolean inBounds(int row, int col){
        if (row > -1 && row < 120 && col > -1 && col < 160)
            return true;
        return false;
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