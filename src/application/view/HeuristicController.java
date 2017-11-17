package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class HeuristicController {

    @FXML
    private RadioButton uniformButton;
    @FXML
    private RadioButton aStarButton;
    @FXML
    private RadioButton weightedButton;
    @FXML
    private RadioButton sequentialButton;

    public void start(Stage primaryStage){
        setToggle();
    }

    private void setToggle(){
        final ToggleGroup group = new ToggleGroup();
        uniformButton.setToggleGroup(group);
        aStarButton.setToggleGroup(group);
        weightedButton.setToggleGroup(group);
        sequentialButton.setToggleGroup(group);

        uniformButton.setSelected(true);
    }
}
