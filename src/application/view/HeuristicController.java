package application.view;

import java.io.File;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    private boolean filesExist;

    private File[] graphFiles;

    private ObservableList<File> obslist;



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
    }

    @FXML
    private void solveGraph(){
        /** Button to solve the loaded graph. **/
    }
}
