package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import store.StoreManager;
import types.Song;

public class SongLibController {
    @FXML
    private TextField song, artist, album, year;

    @FXML
    private Text alert;

    private StoreManager storeManager;

    public SongLibController() {
        this.storeManager = new StoreManager();
    }

    public void initialize() {
        this.loadSongs();
    }

    private void loadSongs() {

    }

    public void addSong(ActionEvent e) {
        alert.setVisible(false);
        final String songVal = song.getText().trim();
        final String artistVal = artist.getText().trim();

        if (songVal.isEmpty() || artistVal.isEmpty()) {
            alert.setVisible(true);
            return;
        }

        Song newSong = new Song(songVal, artistVal);
        final String yearVal = year.getText().trim();
        final String albumVal = album.getText().trim();

        if (StoreManager.isUnique(newSong)) {
            newSong.album = albumVal.isEmpty() ? newSong.album : albumVal;
            newSong.year = yearVal.isEmpty() ? newSong.year : Integer.parseInt(yearVal);
            this.storeManager.put(newSong);
        }
    }

    public void removeSong(ActionEvent e) {

    }

    public void showEdit(ActionEvent e) {

    }

    public void editSong(ActionEvent e) {

    }

}