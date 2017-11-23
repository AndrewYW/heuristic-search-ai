package application.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.lang.StringBuilder;

public class GraphFile {
    public File file;
    public int[][] coords = new int[8][2];
    public int[] start = new int[2];   //Start coordinates: [0] is row, [1] is col
    public int[] goal = new int[2];    //Goal coordinates
    public Node[][] graph = new Node[120][160]; //The 2d node array associated with the file

    public GraphFile(File file){
        /*Get all data from file to generate the GraphFile. */
        this.file = file;
        initialize();
    }

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

    public Node getStart(){
        return this.graph[this.start[0]][this.start[1]];
    }

    public Node getGoal(){
        return this.graph[this.goal[0]][this.goal[1]];
    }
    public String toString(){
        /** toString override for the observableList. Trims extensions from filename. **/
        String filename = this.file.getName();
        if (filename.indexOf(".") > 0){
            filename = filename.substring(0, filename.lastIndexOf("."));
        }
        return filename;
    }
}
