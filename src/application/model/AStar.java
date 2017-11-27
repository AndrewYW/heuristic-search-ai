package application.model;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStar {
    private Node start;
    private Node goal;
    private float weight;
    private PriorityQueue<Node> fringe;
    private ArrayList<Node> closed;

    public AStar(Node start, Node goal, float weight){
        this.start = start;
        this.goal = goal;
        this.weight = weight;
    }

    public boolean solve(){
        System.out.println("Solving map...");
        this.start.setgVal(0);
        this.start.setParent(this.start);
        this.fringe = new PriorityQueue<>();
        this.closed = new ArrayList<>();

        this.start.sethVal(this.goal);
        this.start.setfVal(this.start.getgVal() + (this.start.gethVal()* this.weight));
        this.fringe.add(this.start);

        while(!this.fringe.isEmpty()){
            Node s = this.fringe.poll();
            if(s == this.goal){
                System.out.println("Solution found...");
                return true;
            }
            this.closed.add(s);
            for(Node child : s.getChildren()){
                if(!this.closed.contains(child)){
                    if(!this.fringe.contains(child)){
                        child.setgVal(Float.MAX_VALUE);
                        child.setParent(null);
                    }
                    updateNode(s, child);
                }
            }
        }

        return false;
    }

    private void updateNode(Node s, Node child){
        if(s.getgVal() + s.getCost(child) < child.getgVal()){
            child.setgVal(s.getgVal() + s.getCost(child));
            child.setParent(s);
            if(this.fringe.contains(child))
                this.fringe.remove(child);
            child.sethVal(this.goal);
            child.setfVal(child.getgVal() + (child.gethVal() * this.weight));
            this.fringe.add(child);
        }
    }
    public Node getGoal(){ return this.goal; }
    public Node getStart(){ return this.start; }

    private float heuristicFunction(Node node){
        int row1 = node.getRow();
        int col1 = node.getCol();
        int row2 = this.goal.getRow();
        int col2 = this.goal.getCol();

        double aSquared = Math.pow((row1-row2), 2);
        double bSquared = Math.pow((col1-col2), 2);

        return (float)Math.sqrt(aSquared+bSquared);
    }
}
