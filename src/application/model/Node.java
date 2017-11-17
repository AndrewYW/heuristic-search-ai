package application.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int depth;
    public int row, col;
    public int cost;
    public int totalCost;
    public char type;
    public int b_row, b_col;
    public Node Parent;
    public List<Node> Children = new ArrayList<Node>();

    public Node(int row, int col, char type){
        this.row = row;
        this.col = col;
        this.type = type;
    }

    /*

    public Node(int row, int column, int b_row, int b_col){
        this.row = row;
        this.column = column;
        depth = 0;
        totalCost = 0;
        this.b_row = b_row;
        this.b_col = b_col;
    }

    public Node(int row, int column, int cost, Node Parent, int b_row, int b_col){
        this.row = row;
        this.column = column;
        this.Parent = Parent;
        this.cost = cost;
        this.totalCost = Parent.totalCost + cost;
        this.depth = Parent.depth + 1;
        this.b_row = b_row;
        this.b_col = b_col;
    }


    public void addChild(Node child){
        Children.add(child);
    }

    public String getCoordinates(){
        return row + " " + column;
    }
    */

}