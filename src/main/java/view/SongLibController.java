package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author Ryan Brandt, Ian Barry
 */
public class SongLibController {
    @FXML
    TextField song;
    @FXML
    TextField artist;
    @FXML
    TextField album;
    @FXML
    TextField year;

    @FXML
    Text alert;

    public void addSong(ActionEvent e) {
        alert.setVisible(false);
        String songVal = song.getText().trim();
        String artistVal = artist.getText().trim();
        if (songVal.isEmpty() || artistVal.isEmpty()) {
            alert.setVisible(true);
            return;
        } else {
            // check (songVal, artistVal) unique, add to store
        }
    }

    public void removeSong(ActionEvent e) {
        // remove from store
    }

    public void showEdit(ActionEvent e) {
        // reveal edit form
    }

    public void editSong(ActionEvent e) {
        // update store
    }
}