package app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SongLib extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane root = makeGridPane();
        Scene scene = new Scene(root, 750, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Song Library");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static GridPane makeGridPane() {

        /**
         * Text Fields
         */
        Text addText = new Text("Add Song");

        /**
         * Input Fields
         */
        final TextField songTitle = new TextField();
        songTitle.setPromptText("Song Title");

        final TextField artistName = new TextField();
        artistName.setPromptText("Artist Name");

        final TextField songYear = new TextField();
        songYear.setPromptText("Song Year");

        final TextField songAlbum = new TextField();
        songAlbum.setPromptText("Album");

        Button addSubmit = new Button("Add");

        GridPane gridPane = new GridPane();
        gridPane.add(addText, 0, 0);
        gridPane.add(songTitle, 0, 1);
        gridPane.add(artistName, 2, 1);
        gridPane.add(songYear, 3, 1);
        gridPane.add(songAlbum, 4, 1);
        gridPane.add(addSubmit, 0, 2);

        /**
         * Styling
         */
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        GridPane.setValignment(addText, VPos.BOTTOM);

        /**
         * Event Handling
         */
        addSubmit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String songTitleVal = songTitle.getText();
                String artistNameVal = artistName.getText();
                // do stuff
            }
        });

        return gridPane;
    }

}