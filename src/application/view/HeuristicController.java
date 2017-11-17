package application.view;

import application.model.Node;
import java.io.File;

import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;


public class HeuristicController {

    @FXML
    private ComboBox algsBox;
    @FXML
    private ComboBox fileBox;
    @FXML
    private Label dirLabel;
    @FXML
    private ListView<File> fileList;
    @FXML
    private GridPane graph;

    private boolean filesExist;

    private File[] graphFiles;

    private ObservableList<File> obslist;

    private Node[][] currentGraph = new Node[120][160];
    private String[] coordinates = new String[10];



    public void start(Stage primaryStage){
        setAlgBox();
        loadFileBox();

    }

    private void setAlgBox(){
        algsBox.getItems().addAll(
                "Uniform-cost",
                        "A* search",
                        "Weighted A*",
                        "Sequential Heuristic A*");

        algsBox.setValue("Uniform-cost");
    }

    private void loadFileBox(){
        File dir = new File("src/application/data");

        if(dir.exists()){
            if(dir.list().length > 0){
                String label = "Graph count: " + Integer.toString(dir.list().length);
                filesExist = true;
                dirLabel.setText(label);
                //Load data files into box
                graphFiles = dir.listFiles();
                for (File file : graphFiles){
                    fileBox.getItems().add(file.getName());
                }
            }
            else{
                filesExist = false;
                dirLabel.setText("Graph Count: 0");
            }
        }
    }

    /** TODO
     * Convert File combo box into observable list for easier viewing.
     */
    private void loadFileList(){
        obslist = FXCollections.observableArrayList();
    }

    @FXML
    private void handleGenerate(){
        /** Button method to generate random graphs. **/
        if(filesExist){
            //Files already exist, asks for confirmation
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("WARNING");
            alert.setHeaderText("Graphs already exist");
            alert.setContentText("Creating new graphs will erase old files. Confirm to continue.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }
        else{
            //Free to generate files
        }
    }

    @FXML
    private void handleDelete(){
        /** Button to delete selected file. **/
        String filePath = "src/application/data/" + fileBox.getValue();
    }

    @FXML
    private void loadGraph(){
        /** Button to load graph onto screen. **/
        String filePath = "src/application/data/" + fileBox.getValue();
        File file = new File(filePath);

        currentGraph = loadNodeArray(file);


    }

    @FXML
    private void solveGraph(){
        /** Button to solve the loaded graph. **/
    }

    private Node[][] loadNodeArray(File file){
        /* Helper method to create a 2d array of nodes given filepath.
           Strips file of first 10 lines to place into coordinates array, then converts into 2d char array and goes from there.
           In addition, it creates the graph to be shown in the anchorpane.
         */
        try {
            Scanner sc = new Scanner(file);

            int c = 0;

            //Store the first 10 lines of the file into the coordinates array.
            while( c < 10 ){
                coordinates[c] = sc.nextLine();
                System.out.println("Coordinates[" + c + "]: " + coordinates[c]);
                c++;
            }

            Node[][] result = new Node[120][160];
            //
            for(int row = 0; row < 120; row++){
                char[] line = sc.nextLine().toCharArray();
                for(int col = 0; col < 160; col++){

                    //Create node and input to node array 'currentGraph'
                    Node node = new Node(row, col, line[col]);
                    result[row][col] = node;

                    //Create Panes for graph.
                    Pane pane = new Pane();
                    switch(node.type){
                        case '0':       //Blocked Cell
                            pane.setStyle("-fx-background-color: black");
                            break;
                        case '1':       //Regular unblocked
                            pane.setStyle("-fx-background-color: ghostwhite");
                            break;
                        case '2':       //Hard to traverse
                            pane.setStyle("-fx-background-color: dimgray");
                            break;
                        case 'a':       //Regular highway
                            pane.setStyle("-fx-background-color: cyan");
                            break;
                        case 'b':       //Hard traverse highway
                            pane.setStyle("-fx-background-color: darkblue");
                            break;
                        default:
                            System.out.println("Error parsing char: not 0, 1, 2, a, or b");
                    }
                    graph.add(pane, col, row);
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

}
