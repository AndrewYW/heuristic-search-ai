package application.model;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * FileGenerator class, used to create each individual file.
 * Various methods to adjust map elements, as well as one to write to file.
 * @author Andrew Wang
 */
public class FileGenerator {
    /**
     * 2d char array of the map. Each char is a type of terrain.
     */
    private char[][] types;
    /**
     * 2d int array of start coordinates. 10 different coordinates, [i][0] is row, [i][1] is column.
     */
    private int[][] start;
    /**
     * 2d int array of goal coordinates. 10 different coordinates, [i][0] is row, [i][1] is column.
     */
    private int[][] goal;
    /**
     * 2d int array of hard traversal centers. 8 different coordinates, [i][0] is row, [i][1] is column.
     */
    private int[][] hardCoords;

    /**
     * Default constructor for FileGenerator class.
     * On initialization, will create a 2d char array of '1' for use later.
     * Other fields are set to hold file information: starts, goals, and coordinates of hard traversal centers.
     */
    public FileGenerator(){
        this.types = new char[120][160];
        for(char[] row : types)
            Arrays.fill(row, '1');
        this.start = new int[10][2];
        this.goal = new int[10][2];
        this.hardCoords = new int[8][2];
    }

    /**
     * Helper method to adjust hard to traverse regions.
     * Randomly selects a position on the map, then adjusts the 31x31 region surrounding it.
     * Every spot around it has 50% chance to become a hard to traverse region.
     * The 8 randomly selected regions are stored for use in the file generator as well.
     *
     */
    public void setHards(){
        Random rng = new Random();
        int count = 0;
        while(count < 8) {
            boolean repeat = false;
            int row = rng.nextInt(120);
            int col = rng.nextInt(160);

            for (int i = 0; i < count; i++) {
                if ((this.hardCoords[i][0] == row) && (this.hardCoords[i][1] == col))
                    repeat = true;
            }

            if(!repeat){
                this.hardCoords[count][0] = row;
                this.hardCoords[count][1] = col;

                for (int k = -15; k < 16; k++) {
                    for (int j = -15; j < 16; j++) {
                        if ((row + k > -1) && (col + j > -1) && (row + k < 120) && (col + j < 160)) {
                            if (rng.nextBoolean())
                                this.types[row + k][col + j] = '2';
                        }
                    }
                }
                count++;
            }
        }
    }

    /**
     * Method to create the highways on a given map.
     */
    public void setHighways(){

        ArrayList<Tuple> highways = new ArrayList<>();
        int tries = 0;
        int paths = 0;

        while(paths < 4){
            if(tries == 500) {
                highways.clear();
                tries = 0;
            } else {
                System.out.println("Tries: " + Integer.toString(tries));
                ArrayList<Tuple> path = makePath(highways, setStartCoord());
                if (path != null) {
                    System.out.println("Path accepted: " + Integer.toString(paths+1));
                    highways.addAll(path);
                    paths++;
                } else {
                    tries++;
                }
            }
        }

        for(Tuple tuple : highways){
            if(this.types[tuple.getRow()][tuple.getCol()] == '1')
                this.types[tuple.getRow()][tuple.getCol()] = 'a';
            else
                this.types[tuple.getRow()][tuple.getCol()] = 'b';
        }
    }

    /**
     * Helper method to create a path of highway cells.
     * @param highways The arraylist containing all highway cells
     * @param start    The starting position of a path
     * @return         One path to be added to highways. Null if the path fails.
     */
    private ArrayList<Tuple> makePath(ArrayList<Tuple> highways, Tuple start) {
        ArrayList<Tuple> path = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int dir = start.getBorder();
        int currentRow = row;
        int currentCol = col;

        while (true) {
            //System.out.println("Current row: " + Integer.toString(row) + ", current col: " + Integer.toString(col) + ", current dir: " + Integer.toString(dir));

            for (int i = 0; i < 20; i++) {
                Tuple tuple = null;

                switch (dir) {       //Create the next tuple depending on direction.
                    case 1:
                        tuple = new Tuple(row + i, col);
                        currentRow = row + i;
                        //System.out.println("New Tuple, row: " + Integer.toString(row+i) + ", col: " + Integer.toString(col));
                        break;
                    case 2:
                        tuple = new Tuple(row - i, col);
                        currentRow = row - i;
                        //System.out.println("New Tuple, row: " + Integer.toString(row-i) + ", col: " + Integer.toString(col));

                        break;
                    case 3:
                        tuple = new Tuple(row, col + i);
                        currentCol = col + i;
                        //System.out.println("New Tuple, row: " + Integer.toString(row) + ", col: " + Integer.toString(col + i));

                        break;
                    case 4:
                        tuple = new Tuple(row, col - i);
                        //System.out.println("New Tuple, row: " + Integer.toString(row) + ", col: " + Integer.toString(col - i));

                        currentCol = col - i;
                        break;
                }

                if (!isIn(highways, tuple) && !isIn(path, tuple)) {   //Not in already existing highway
                    if (!exceedsBorder(tuple)) {                      //Not border yet
                        path.add(tuple);
                    } else {                                          //Is border
                        if (path.size() > 99)                         //Size is enough: return the path
                            return path;
                        return null;
                    }
                } else {
                    return null;
                }
            }
            dir = changeDir(dir);
            switch (dir){
                case 1:
                    row = currentRow + 1;
                    col = currentCol;
                    break;
                case 2:
                    row = currentRow - 1;
                    col = currentCol;
                    break;
                case 3:
                    row = currentRow;
                    col = currentCol + 1;
                    break;
                case 4:
                    row = currentRow;
                    col = currentCol - 1;
            }
        }
    }

    /**
     * Checks to see if an arraylist contains a given tuple.
     * @param tuples The arraylist of tuples to check.
     * @param tuple  The tuple to check in the arraylist.
     * @return       True if tuple is found in the arraylist.
     */
    private boolean isIn(ArrayList<Tuple> tuples, Tuple tuple){
        for(Tuple t : tuples){
            if(t.equals(tuple))
                return true;
        }
        return false;
    }

    /**
     * Helper method to change the direction the path makes after 20 steps.
     * @param direction The current direction
     * @return          New direction. 1 = down, 2 = up, 3 = right, 4 = left.
     */
    private int changeDir(int direction){
        Random rng = new Random();
        int newDirection = 0;
        switch(direction){
            case 1:
                if(rng.nextDouble() < .6)
                    newDirection = 1;
                else if(rng.nextBoolean())
                    newDirection = 3;
                else
                    newDirection = 4;
                break;
            case 2:
                if(rng.nextDouble() < .6)
                    newDirection = 2;
                else if(rng.nextBoolean())
                    newDirection = 3;
                else
                    newDirection = 4;
                break;
            case 3:
                if(rng.nextDouble() < .6)
                    newDirection = 3;
                else if(rng.nextBoolean())
                    newDirection = 1;
                else
                    newDirection = 2;
                break;
            case 4:
                if(rng.nextDouble() < .6)
                    newDirection = 4;
                else if (rng.nextBoolean())
                    newDirection = 1;
                else
                    newDirection = 2;
                break;
        }
        return newDirection;
    }

    /**
     * Checks an individual tuple to see if it exceeds the boundaries of the map.
     * @param tuple The tuple to be checked.
     * @return      True if it exceeds, false if not.
     */
    private boolean exceedsBorder(Tuple tuple){
        if(tuple.getCol() < 0 || tuple.getCol() > 159 || tuple.getRow() < 0 || tuple.getRow() > 119){
            return true;
        }
        return false;
    }

    /**
     * Helper method to select the starting coordinates of a highway path.
     * Randomly selects a border, then checks to see if it's not a corner position.
     * @return Tuple with border field initialized to 1, 2, 3, or 4.
     */
    private Tuple setStartCoord(){
        Random rng = new Random();
        int row = 0;
        int col = 0;
        int border = 0;
        boolean setStart = false;
        while(!setStart) {
            if(rng.nextBoolean()){
                if(rng.nextBoolean()) {     //Top border
                    row = 0;
                    col = rng.nextInt(159) + 1;
                    border = 1;
                }else{                      //Bottom border
                    row = 119;
                    col = rng.nextInt(159) + 1;
                    border = 2;
                }
            }else{                          //Left border
                if(rng.nextBoolean()){
                    row = rng.nextInt(119) + 1;
                    col = 0;
                    border = 3;
                }else{                      //Right border
                    row = rng.nextInt(119) + 1;
                    col = 159;
                    border = 4;
                }
            }

            if( !((row == 0) && (col == 0)) && !((row == 119) && (col == 159)) && !((row == 0) && (col == 159)) && !((row == 119) && (col == 0)))
                setStart = true;
        }

        return new Tuple(row, col, border);
    }

    /**
     * Method to set 20% of all positions as blocked cells.
     * The only blockable cells are non highway, unblocked cells.
     **/
    public void setBlocked(){
        Random rng = new Random();
        int blocked = 0;

        while(blocked != 3840){
            int row = rng.nextInt(120);
            int col = rng.nextInt(160);

            if(this.types[row][col] == '1' || this.types[row][col] == '2'){
                this.types[row][col] = '0';
                blocked++;
            }
        }
    }

    /**
     * Method to set the 10 start-goal pairs of a given map.
     * Fills in the 2d int array start and goal fields of the FileGenerator.
     */
    public void setNodes(){
        Random rng = new Random();
        int count = 0;
        while(count < 10){
            boolean repeat = false;
            int startRow = rng.nextInt(40);
            int startCol = rng.nextInt(40);
            if (startRow > 20)
                startRow += 80;
            if (startCol > 20)
                startCol += 120;

            if(this.types[startRow][startCol] != '0'){
                this.start[count][0] = startRow;
                this.start[count][1] = startCol;
                boolean goal = false;
                while(!goal) {
                    int goalRow = rng.nextInt(40);
                    int goalCol = rng.nextInt(40);
                    if (goalRow > 20)
                        goalRow += 80;
                    if (goalCol > 20)
                        goalCol += 120;

                    if (this.types[goalRow][goalCol] != '0') {
                        for (int i = 0; i < count; i++) {
                            if ((this.goal[i][0] == goalRow) && (this.goal[i][1] == goalCol))
                                repeat = true;
                        }
                        if (!repeat) {
                            if (euclidDistance(startRow, startCol, goalRow, goalCol)) {
                                this.goal[count][0] = goalRow;
                                this.goal[count][1] = goalCol;
                                goal = true;
                                count++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Helper method to determine euclidean distance between two locations.
     * Used to make sure start and end cell are at least 100 units apart from each other.
     * @param xRow Row of start cell
     * @param xCol Column of start cell
     * @param yRow Row of goal cell
     * @param yCol Col of goal cell
     * @return     True if distance is greater than 100 cell units
     */
    private boolean euclidDistance(int xRow, int xCol, int yRow, int yCol){
        double aSquared = Math.pow((xRow - yRow), 2);
        double bSquared = Math.pow((xCol - yCol), 2);

        if(Math.sqrt(aSquared+bSquared) < 100){
            return false;
        }
        return true;
    }

    /**
     * Helper method to create a file in the src/data/ folder. Meant to be used in a nested for loop
     * to get the correct naming sequence. For each map generated, writeFile will create 10 files, with
     * different start-goal pairs for each.
     * @param mapNum The ith map generated.
     */
    public void writeFile(int mapNum) {
        for (int i = 0; i < 10; i++) {
            String filepath = "src/application/data/Map" + Integer.toString(mapNum+1) + "Set" + Integer.toString(i+1) + ".txt";
            PrintWriter write;
            try {
                File file = new File(filepath);
                file.createNewFile();
                write = new PrintWriter(file);
                write.println(Integer.toString(this.start[i][0]) + " " + Integer.toString(this.start[i][1]));
                write.println(Integer.toString(this.goal[i][0]) + " " + Integer.toString(this.goal[i][1]));

                for (int[] row : this.hardCoords) {
                    write.println(Integer.toString(row[0]) + " " + Integer.toString(row[1]));
                }

                for (char[] row : this.types) {
                    write.println(String.valueOf(row));
                }

                write.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
