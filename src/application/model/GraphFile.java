package application.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.lang.StringBuilder;

/**
 * GraphFile class, contains information loaded from each file.
 * Methods present to retrieve information.
 * @author Andrew Wang
 */
public class GraphFile {
    /**
     * File associated with GraphFile.
     */
    public File file;
    /**
     * 2d int array, holding the coordinates of each hard to traverse center.
     */
    public int[][] coords = new int[8][2];
    /**
     * Int array, holding the start coordinates. [0] is row, [1] is column.
     */
    public int[] start = new int[2];
    /**
     * Int array, holding the goal coordinates. [0] is row, [1] is column.
     */
    public int[] goal = new int[2];
    /**
     * 2d Node array, a holder of nodes created from the given file.
     */
    public Node[][] graph = new Node[120][160];

    /**
     * Default constructor.
     * @param file File to load data from into a GraphFile.
     */
    public GraphFile(File file){
        this.file = file;
        initialize();
    }

    /**
     * Helper method to initialize the GraphFile.
     * Reads line by line through a file, and fills out fields.
     */
    public void initialize(){
        try{
            Scanner sc = new Scanner(this.file);
            int c = 0;

            //Load the first 10 lines into the appropriate fields.
            while( c < 10 ){
                String line = sc.nextLine();
                String[] splits = line.split(" ");
                if( c == 0 ){
                    this.start[0] = Integer.parseInt(splits[0]);
                    this.start[1] = Integer.parseInt(splits[1]);
                    c++;
                }
                else if( c == 1){
                    this.goal[0] = Integer.parseInt(splits[0]);
                    this.goal[1] = Integer.parseInt(splits[1]);
                    c++;
                }
                else{
                    this.coords[c-2][0] = Integer.parseInt(splits[0]);
                    this.coords[c-2][1] = Integer.parseInt(splits[1]);
                    c++;
                }
            }

            //Create the node matrix associated with the file.
            for(int row = 0; row < 120; row++){
                char[] line = sc.nextLine().toCharArray();
                for(int col = 0; col < 160; col++){

                    Node node = new Node(row, col, line[col]);
                    this.graph[row][col] = node;
                }
            }

            sc.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Method used for loading Textlabel data for hard to traverse coordinates.
     * Utilizes a StringBuilder to construct a string in the form "(row, col)".
     * Uses a switch statement to specify which TextLabel to set.
     * @param pos The specific textlabel to fill.
     * @return    String to fill the correct textlabel with.
     */
    public String getCoords(int pos){
        StringBuilder res = new StringBuilder().append("(");
        switch(pos){
            case 1:
                res.append(this.start[0]).append(",").append(this.start[1]).append(")");
                break;
            case 2:
                res.append(this.goal[0]).append(",").append(this.goal[1]).append(")");
                break;
            case 3:
                res.append(this.coords[0][0]).append(",").append(this.coords[0][1]).append(")");
                break;
            case 4:
                res.append(this.coords[1][0]).append(",").append(this.coords[1][1]).append(")");
                break;
            case 5:
                res.append(this.coords[2][0]).append(",").append(this.coords[2][1]).append(")");
                break;
            case 6:
                res.append(this.coords[3][0]).append(",").append(this.coords[3][1]).append(")");
                break;
            case 7:
                res.append(this.coords[4][0]).append(",").append(this.coords[4][1]).append(")");
                break;
            case 8:
                res.append(this.coords[5][0]).append(",").append(this.coords[5][1]).append(")");
                break;
            case 9:
                res.append(this.coords[6][0]).append(",").append(this.coords[6][1]).append(")");
                break;
            case 10:
                res.append(this.coords[7][0]).append(",").append(this.coords[7][1]).append(")");
                break;
            default:
                System.out.println("Error in getCoords.");
        }
        return res.toString();
    }

    /**
     * Method to get the start coordinates.
     * @return Node that the start position is on.
     */
    public Node getStartNode(){
        return this.graph[this.start[0]][this.start[1]];
    }

    /**
     *Method to get the goal coordinate node.
     * @return Node that the goal position is on.
     */
    public Node getGoalNode(){ return this.graph[this.goal[0]][this.goal[1]]; }

    /**
     * toString override for the observableList. Trims extensions from filename.
     * @return String of filename, minus the extension.
     */
    @Override
    public String toString(){
        String filename = this.file.getName();
        if (filename.indexOf(".") > 0){
            filename = filename.substring(0, filename.lastIndexOf("."));
        }
        return filename;
    }
}
