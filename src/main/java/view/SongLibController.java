package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import store.StoreManager;
import types.Song;

/**
 * @author Ryan Brandt
 */
public class SongLibController {
    @FXML
    private TextField song, artist, album, year;

    @FXML
    private Text alert;

    @FXML
    private TableView<Song> table;

    @FXML
    private TableColumn<Song, String> songCol, artistCol;

    private StoreManager storeManager;

    /**
     * Lifecycle Methods
     */
    public SongLibController() {
        this.storeManager = new StoreManager();
    }

    public void initialize() {
        songCol.setCellValueFactory(new PropertyValueFactory<Song, String>("song"));
        songCol.setSortType(TableColumn.SortType.ASCENDING);
        artistCol.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        this.loadSongs();
    }

    /**
     * Event Handlers
     */
    public void addSong(ActionEvent e) {
        alert.setVisible(false);
        final String songVal = song.getText().trim();
        final String artistVal = artist.getText().trim();

        if (songVal.isEmpty() || artistVal.isEmpty()) {
            this.showAlert("required");
            return;
        }

        Song newSong = new Song(songVal, artistVal);
        final String yearVal = year.getText().trim();
        final String albumVal = album.getText().trim();

        if (this.storeManager.isUnique(newSong)) {
            if (!albumVal.isEmpty())
                newSong.album = albumVal;
            if (!yearVal.isEmpty())
                newSong.year = Integer.parseInt(yearVal);

            this.storeManager.put(newSong);
            this.showAlert("success");
            this.clearForm("add");
            this.loadSongs();
        } else {
            this.showAlert("unique");
        }
    }

    public void removeSong(ActionEvent e) {
        // TODO call delete and reload
    }

    public void showEdit(ActionEvent e) {
        // TODO show edit form
    }

    public void editSong(ActionEvent e) {
        // TODO call update and reload
    }

    /**
     * Utilities
     */
    private void loadSongs() {
        table.setItems(this.storeManager.getSongList());
        table.getSortOrder().add(songCol);
    }

    private void clearForm(String form) {
        switch (form) {
        case "add":
            song.clear();
            artist.clear();
            album.clear();
            year.clear();
            break;
        case "edit":
            // TODO
        }
    }

    private void showAlert(String alertType) {
        switch (alertType) {
        case "required":
            alert.setText("Please include a Song Title and Artist Name!");
            break;
        case "unique":
            alert.setText("Song Title and Artist Name must form a unique pair!");
            break;
        case "success":
            alert.setText("Song added!");
        }
        alert.setVisible(true);
    }

}