package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Ryan Brandt, Ian Barry
 */
public class SongLib extends Application {

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/songLib.fxml"));

        try {
            GridPane root = (GridPane) loader.load();
            Scene scene = new Scene(root, 800, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Song Library");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}