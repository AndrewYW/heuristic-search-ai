package application.model;

public class AStar {
    private Node start;
    private Node goal;
    private float hVal;
    private Node[] solution;

    public AStar(Node start, Node goal, float heuristic){
        this.start = start;
        this.goal = goal;
        this.hVal = heuristic;
    }

    public boolean solve(){
        return false;
    }

    public Node[] getSolution(){
        return this.solution;
    }
}
