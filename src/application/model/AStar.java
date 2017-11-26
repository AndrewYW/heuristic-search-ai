package application.model;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStar {
    private Node start;
    private Node goal;
    private float hVal;
    private float weight;
    private PriorityQueue<Node> fringe;
    private ArrayList<Node> closed;
    private ArrayList<Node> solution;

    public AStar(Node start, Node goal, float heuristic, float weight){
        this.start = start;
        this.goal = goal;
        this.hVal = heuristic;
        this.weight = weight;
        solution = new ArrayList<>();
    }

    public boolean solve(){
        start.setgVal(0);
        start.setParent(start);
        this.fringe = new PriorityQueue<>();
        this.closed = new ArrayList<>();

        start.setfVal(start.getgVal() + (this.hVal * this.weight));
        this.fringe.add(start);

        while(!this.fringe.isEmpty()){
            Node s = this.fringe.poll();
            if(s == this.goal){
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
            child.setfVal(child.getgVal() + (this.hVal * this.weight));
            this.fringe.add(child);
        }
    }

    public ArrayList<Node> getSolution(){
        return this.solution;
    }
}
