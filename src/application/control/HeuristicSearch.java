package application.control;

import application.view.HeuristicController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HeuristicSearch extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Heuristic.fxml"));
        AnchorPane rootLayout = loader.load();

        HeuristicController hControl = loader.getController();
        hControl.start(primaryStage);

        Scene scene = new Scene(rootLayout);
        primaryStage.setTitle("Heuristic Search - Andrew Wang & Mauricio Alvarez");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }
}
