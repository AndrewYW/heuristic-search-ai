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
    private PriorityQueue<Node> euclidOpen, euclidSOpen, manhattanOpen, chebyOpen, octileOpen;
    private PriorityQueue<Node> euclidClosed, euclidSClosed, manhattanClosed, chebyClosed, octileClosed;

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

    public float key(Node s, int i){
        switch(i){
            case 0:
                s.euclidH = s.getEuclid(this.goal, 0);
                return (float)(s.euclidG + weight*s.euclidH);
            case 1:
                s.euclidSH = s.getEuclid(this.goal, 1);
                return (float)(s.euclidSG + weight*s.euclidSH);
            case 2:
                s.manhattanH = s.getManhattan(this.goal);
                return (float)(s.manhattanG + weight*s.manhattanH);
            case 3:
                s.chebyH = s.getDiagonal(this.goal, 3);
                return (float)(s.chebyG + weight*s.chebyH);
            case 4:
                s.octileH = s.getDiagonal(this.goal, 4);
                return (float)(s.octileG + weight*s.octileH);
        }
        return 0;
    }

    public void ExpandState(Node s, int i){
        switch(i){
            case 0:

        }
    }

    public void sequential(Node start, Node goal){
        for(int i = 0; i < 5; i++){
            init(start, goal, i);
        }
        while(euclidOpen.peek().euclidF < Float.MAX_VALUE){
            for(int i = 1; i < 5; i++){
                float openIMinKey;
                float gi;
                switch(i){
                    case 1:
                        //openIMinKey = euclidSOpen.peek().euclidSF;
                        //gi = 
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }



            }
        }
    }

    private void init(Node start, Node goal, int i){
        switch(i){
            case 0:
                euclidOpen = new PriorityQueue<>();
                euclidClosed = new PriorityQueue<>();
                start.euclidG = 0;
                goal.euclidG = 0;
                start.euclidP = null;
                goal.euclidP = null;
                start.euclidF = key(start, 0);
                euclidOpen.add(start);
                break;
            case 1:
                euclidSOpen = new PriorityQueue<>();
                euclidSClosed = new PriorityQueue<>();
                start.euclidSG = 0;
                goal.euclidSG = 0;
                start.euclidSP = null;
                goal.euclidSP = null;
                start.euclidSF = key(start, 1);
                euclidSOpen.add(start);
                break;
            case 2:
                manhattanOpen = new PriorityQueue<>();
                manhattanClosed = new PriorityQueue<>();
                start.manhattanG = 0;
                goal.manhattanG = 0;
                start.manhattanP = null;
                goal.manhattanP = null;
                start.manhattanF = key(start, 2);
                manhattanOpen.add(start);
                break;
            case 3:
                chebyOpen = new PriorityQueue<>();
                chebyClosed = new PriorityQueue<>();
                start.chebyG = 0;
                goal.chebyG = 0;
                start.chebyP = null;
                goal.chebyP = null;
                start.chebyF = key(start, 3);
                chebyOpen.add(start);
                break;
            case 4:
                octileOpen = new PriorityQueue<>();
                octileClosed = new PriorityQueue<>();
                start.octileG = 0;
                goal.octileG = 0;
                start.octileP = null;
                goal.octileP = null;
                start.octileF = key(start, 4);
                octileOpen.add(start);
                break;
        }
    }
}
