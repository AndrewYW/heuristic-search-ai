package application.model;

import java.io.File;

public class FileListItem {
    public File file;
    public String filepath;
    public int[][] coords = new int[8][2];
    public int[] start = new int[2];
    public int[] goal = new int[2];
    public Node[][] graph;

    public FileListItem(File file){
        /*Get all data from file to generate the listitem.*/
        this.file = file;
        String path = "src/application/data" + file.getName();


    }
}
