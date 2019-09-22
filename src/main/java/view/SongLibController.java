package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import store.StoreManager;
import types.Song;

/**
 * @author Ryan Brandt
 */
public class SongLibController {
    @FXML
    private TextField song, artist, album, year, songEdit, artistEdit, albumEdit, yearEdit;

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

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) this.showSelected(table.getSelectionModel().getSelectedItem());
        });

        this.loadSongs();
        table.getSelectionModel().selectFirst();
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
        final String yearVal = year.getText().trim();
        final String albumVal = album.getText().trim();

        if(!yearVal.isEmpty() && !isNum(yearVal)) {
            this.showAlert("year");
            return;
        }

        Song newSong = new Song(songVal, artistVal);

        if (this.storeManager.isUnique(newSong)) {
            if (!albumVal.isEmpty()) newSong.album = albumVal;
            if (!yearVal.isEmpty()) newSong.year = Integer.parseInt(yearVal);

            this.storeManager.put(newSong);
            this.showAlert("success");
            song.clear(); artist.clear(); album.clear(); year.clear();
            this.loadSongs();
            table.getSelectionModel().select(newSong);
        } else {
            this.showAlert("unique");
        }
    }

    public void removeSong(ActionEvent e) {
        // TODO call delete and reload
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

    private boolean isNum(String year) {
        try {
            Integer.parseInt(year);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private void showSelected(Song selected) {
        songEdit.setText(selected.song);
        artistEdit.setText(selected.artist);
        if(!selected.album.equals("null")) albumEdit.setText(selected.album);
        if(selected.year != -1) yearEdit.setText(Integer.toString(selected.year));
    }

    private void showAlert(String alertType) {
        switch (alertType) {
        case "required":
            alert.setText("Please include a Song Title and Artist Name!");
            alert.setFill(Color.RED);
            break;
        case "unique":
            alert.setText("Song Title and Artist Name must form a unique pair!");
            alert.setFill(Color.RED);
            break;
        case "success":
            alert.setText("Song added!");
            alert.setFill(Color.GREEN);
            break;
        case "year":
            alert.setText("Year must be a number!");
            alert.setFill(Color.RED);
        }
        alert.setVisible(true);
    }

}