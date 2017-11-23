package application.model;


public class Node {
    public int depth;
    public int row, col;
    public float cost;
    public char type;
    public Node parent = null;


    public Node(int row, int col, char type){
        this.row = row;
        this.col = col;
        this.type = type;
    }

}