package application.view;

import application.model.AStar;
import application.model.FileGenerator;
import application.model.GraphFile;
import java.io.File;

import java.util.Optional;

import application.model.Node;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;

/**
 * The Controller for the FXML file.
 * @author Andrew Wang
 * @author Mauricio Alvarez
 */
public class HeuristicController {

    @FXML
    private ComboBox algsBox;
    @FXML
    private ListView<GraphFile> fileList;
    @FXML
    private GridPane graph;
    @FXML
    private Label c1;
    @FXML
    private Label c2;
    @FXML
    private Label c3;
    @FXML
    private Label c4;
    @FXML
    private Label c5;
    @FXML
    private Label c6;
    @FXML
    private Label c7;
    @FXML
    private Label c8;
    @FXML
    private Label c9;
    @FXML
    private Label c10;
    @FXML
    private Label statusLabel;
    @FXML
    private Label nodeRow;
    @FXML
    private Label nodeCol;
    @FXML
    private Label nodeF;
    @FXML
    private Label nodeG;
    @FXML
    private Label nodeH;
    @FXML
    private Label elapsedTime;
    @FXML
    private TextField wField;
    @FXML
    private ComboBox hBox;

    private File[] sysFiles = null;
    private ObservableList<GraphFile> obslist;

    private Node[][] nodes;

    public void start(Stage primaryStage){
        setAlgBox();
        setHBox();
        obslist = FXCollections.observableArrayList();

        loadDirectory();

        //Set listener for file selection changes.
        fileList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> showFileDetails());
    }

    /**
     * Helper method to load the algorithms Combobox.
     */
    private void setAlgBox(){
        algsBox.getItems().addAll(
                "Uniform-cost",
                        "A* search",
                        "Weighted A*",
                        "Sequential Heuristic A*");

        algsBox.setValue("Uniform-cost");
    }

    /**
     * Helper method to load the heuristic functions Combobox.
     */
    private void setHBox(){
        hBox.getItems().addAll(
                "Euclidean distance",
                            "Manhattan distance");

        hBox.setValue("Euclidean distance");
    }

    /**
     * Button to create a set of 50 files. 5 different maps, 10 different start-goal pairs each.
     * Throws an alert, because the file deletes other preloaded files.
     */
    @FXML
    private void generateButton(){
        /** Button method to generate random graphs. **/
        if(sysFiles != null && sysFiles.length > 0){
            //Files already exist, asks for confirmation
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("WARNING");
            alert.setHeaderText("Graphs already exist");
            alert.setContentText("Creating new graphs will erase old files. Confirm to continue.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                /**
                 * User confirms,
                 * File list is cleared?
                 * Generate new files
                 */
                for(File file : sysFiles){
                    file.delete();
                }
                generateFiles();
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }
        else{
            //Free to generate files, no need to clear
            sysFiles = new File[50];
            generateFiles();
        }
    }

    /**
     * Button to load the graph onto the screen.
     * Invokes the showGraph() helper method.
     */
    @FXML
    private void loadGraph(){
        showGraph();
        setChildren();
    }

    private void setChildren(){
        nodes = fileList.getSelectionModel().getSelectedItem().graph;

        for(int row = 0; row < 120; row++){
            for(int col = 0; col < 160; col++){
                nodes[row][col].setChildren(nodes);
            }
        }
    }
    /**
     * Button to actually solve the selected file with selected algorithm.
     */
    @FXML
    private void solveGraph(){
        showGraph();
        String alg =algsBox.getSelectionModel().getSelectedItem().toString();
        GraphFile gfile = fileList.getSelectionModel().getSelectedItem();       //Contains the .graph field, which is the node matrix.
        int heuristic = hBox.getSelectionModel().getSelectedIndex();
        System.out.println(heuristic);
        Node start = gfile.graph[gfile.start[0]][gfile.start[1]];
        Node goal = gfile.graph[gfile.goal[0]][gfile.goal[1]];

        AStar algorithm = null;
        if(alg.equals("Sequential Heuristic A*")){

        } else if(alg.equals("Uniform-cost")){
            System.out.println("Uniform selected");
            algorithm = new AStar(start, goal, 0, heuristic);
        } else if(alg.equals("A* search")){
            System.out.println("A* selected");
            algorithm = new AStar(start, goal, 1, heuristic);
        } else {
            System.out.println("Weighted selected");
            float weight = Float.parseFloat(wField.getText());
            algorithm = new AStar(start, goal, weight, heuristic);
        }

        if(algorithm.solve()){
            Node node = algorithm.getGoal();
            Node startNode = algorithm.getStart();
            System.out.println("Drawing path...");
            drawPath(node, startNode);
            elapsedTime.setText(Long.toString(algorithm.getTime()));
            System.out.println("gVal: " + goal.getgVal());
        }
    }
    private void drawPath(Node goal, Node start){
        Node n = goal;
        while(n != start){
            int row = n.getRow();
            int col = n.getCol();
            //System.out.println("Node - row: " + Integer.toString(row) + " , col: " + Integer.toString(col));
            Pane pane = new Pane();
            pane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setStyle("-fx-background-color: blueviolet");
            graph.add(pane, col, row);

            n = n.getParent();
        }
        Pane pane = new Pane();
        pane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.setStyle("-fx-background-color: blueviolet");
        graph.add(pane, start.getCol(), start.getRow());


    }

    /**
     * Helper method to load the graph onto the GUI screen.
     * Invoked by the loadGraph button.
     */
    private void showGraph(){

        if(fileList.getSelectionModel().isEmpty()){
            return;
        }
        GraphFile gfile = fileList.getSelectionModel().getSelectedItem();

        for(int row = 0; row < 120; row++){
            for(int col = 0; col < 160; col++){
                //System.out.println("row: " + Integer.toString(row) + ", col: " + Integer.toString(col));
                Pane pane = new Pane();
                //System.out.println("Case: " + gfile.graph[row][col].type);
                pane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pane.setOnMouseClicked((MouseEvent e) -> showNodeDetails(pane));

                switch(gfile.graph[row][col].getType()){     //Node.type = char from file.
                    case '0':       //Blocked Cell
                        pane.setStyle("-fx-background-color: darkslategrey");
                        break;
                    case '1':       //Regular unblocked
                        pane.setStyle("-fx-background-color: lightgray");
                        break;
                    case '2':       //Hard to traverse
                        pane.setStyle("-fx-background-color: slategrey");
                        break;
                    case 'a':       //Regular highway
                        pane.setStyle("-fx-background-color: cyan");
                        break;
                    case 'b':       //Hard traverse highway
                        pane.setStyle("-fx-background-color: cyan");

                        break;
                    default:
                        System.out.println("Error parsing char: not 0, 1, 2, a, or b");
                }
                graph.add(pane, col, row);
            }
        }
    }

    /**
     * Helper method to load cell details when clicked.
     * Since the 2D node array and UI are unlinked, the row and column are necessary to find the right node.
     * @// TODO: 11/26/17   Need to finish
     * @param pane The selected UI pane in the Gridpane.
     */
    private void showNodeDetails(Pane pane){
        int row = graph.getRowIndex(pane);
        int col = graph.getColumnIndex(pane);
        System.out.println("Row: " + row + ", col: " + col);
    }

    /**
     * Method to show the details of a loaded file.
     */
    private void showFileDetails(){
        if(fileList.getSelectionModel().isEmpty()){
            return;
        }

        GraphFile gfile = fileList.getSelectionModel().getSelectedItem();
        /**
         * Display fields onto GUI
         */
        c1.setText(gfile.getCoords(1));
        c2.setText(gfile.getCoords(2));
        c3.setText(gfile.getCoords(3));
        c4.setText(gfile.getCoords(4));
        c5.setText(gfile.getCoords(5));
        c6.setText(gfile.getCoords(6));
        c7.setText(gfile.getCoords(7));
        c8.setText(gfile.getCoords(8));
        c9.setText(gfile.getCoords(9));
        c10.setText(gfile.getCoords(10));
    }

    /**
     * Helper method to generate files.
     * 50 files are generated, 5 different maps with 10 different start-goal coordinates.
     */
    private void generateFiles() {
        obslist.clear();
        FileGenerator filegen = null;
        for (int i = 0; i < 5; i++) {
            filegen = new FileGenerator();
            statusLabel.setText("Setting hard blocks..");
            filegen.setHards();
            filegen.setHighways();
            statusLabel.setText("Setting blocked cells..");
            filegen.setBlocked();
            filegen.setNodes();
            filegen.writeFile(i);
        }

        loadDirectory();

    }

    /**
     * Helper method to load the directory into the ListView.
     */
    private void loadDirectory(){
        File dir = new File("src/application/data");

        if(dir.exists()){
            if( dir.list().length > 0 ){
                sysFiles = dir.listFiles(); //Set global file list for other methods.
                for(File file: sysFiles){
                    try {
                        obslist.add(new GraphFile(file));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        fileList.setItems(obslist);
        if(!obslist.isEmpty()){
            fileList.getSelectionModel().selectFirst();
        }

        showFileDetails();
    }
}
