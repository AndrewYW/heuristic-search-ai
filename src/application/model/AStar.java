package application.model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.time.*;

public class AStar {
    private Node start;
    private Node goal;
    private float weight;
    private int heuristic;
    private long time;
    private int fringeSize;
    private PriorityQueue<Node> fringe;
    private ArrayList<Node> closed;

    public AStar(Node start, Node goal, float weight, int hVal){
        this.start = start;
        this.goal = goal;
        this.weight = weight;
        this.heuristic = hVal;
        this.fringeSize = 0;
    }

    public boolean solve(){
        System.out.println("Solving map...");

        Instant before = Instant.now();
        this.start.setgVal(0);
        this.start.setParent(this.start);
        this.fringe = new PriorityQueue<>();
        this.closed = new ArrayList<>();

        this.start.sethVal(this.goal, this.heuristic);
        this.start.setfVal(this.start.getgVal() + (this.start.gethVal()* this.weight));
        this.fringe.add(this.start);

        while(!this.fringe.isEmpty()){
            Node s = this.fringe.poll();
            if(s == this.goal){
                Instant after = Instant.now();
                this.time = Duration.between(before, after).toMillis();
                System.out.println("Solution found...");
                this.fringeSize = closed.size();
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
        this.time = 0;
        return false;
    }

    private void updateNode(Node s, Node child){
        if(s.getgVal() + s.getCost(child) < child.getgVal()){
            child.setgVal(s.getgVal() + s.getCost(child));
            child.setParent(s);
            if(this.fringe.contains(child))
                this.fringe.remove(child);
            child.sethVal(this.goal, this.heuristic);
            child.setfVal(child.getgVal() + (child.gethVal() * this.weight));
            this.fringe.add(child);
        }
    }

    public Node getGoal(){ return this.goal; }
    public Node getStart(){ return this.start; }
    public long getTime(){ return this.time; }
    public int getFringeSize(){ return this.fringeSize; }
}
