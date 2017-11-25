package application.model;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class FileGenerator {
    private char[][] types;
    private int[][] start;
    private int[][] goal;
    private int[][] hardCoords;

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

    private boolean setHighways(){
        return false;
    }

    /**
     * Helper method to set 20% of all positions as blocked cells.
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
     * to get the correct naming sequence.
     * @param mapNum
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
