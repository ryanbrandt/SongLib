package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Ryan Brandt
 */
public class SongLib extends Application {

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/songLib.fxml"));
        try {
            GridPane root = (GridPane) loader.load();
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
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