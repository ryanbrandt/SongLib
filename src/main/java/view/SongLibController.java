package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
 * @author Ian Barry
 */
public class SongLibController {
    @FXML
    private TextField song, artist, album, year, songEdit, artistEdit, albumEdit, yearEdit;

    @FXML
    private Button editSubmit, deleteSubmit;

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
            if (newSelection != null)
                this.showSelected(table.getSelectionModel().getSelectedItem());
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
        final String yearVal = year.getText().trim();
        final String albumVal = album.getText().trim();

        Song newSong = new Song(songVal, artistVal);

        if (this.isValidSong(newSong, true, albumVal, yearVal)) {
            this.storeManager.put(newSong);
            this.showAlert("add success");
            song.clear();
            artist.clear();
            album.clear();
            year.clear();
            this.loadSongs();
            table.getSelectionModel().select(newSong);
        }
    }

    public void removeSong(ActionEvent e) {
        Song toRemove = table.getSelectionModel().getSelectedItem();
        final int removedIndex = table.getSelectionModel().getSelectedIndex();

        this.storeManager.delete(toRemove);
        this.loadSongs();
        this.showAlert("delete success");

        final int numRows = table.getItems().size();
        if (numRows > 0) {
            final int nextIndex = removedIndex != numRows ? removedIndex : removedIndex - 1;
            table.getSelectionModel().select(nextIndex);
        } else {
            songEdit.clear();
            artistEdit.clear();
            albumEdit.clear();
            yearEdit.clear();
        }
    }

    public void editSong(ActionEvent e) {
        Song origSong = table.getSelectionModel().getSelectedItem();
        final String songVal = songEdit.getText().trim();
        final String artistVal = artistEdit.getText().trim();
        final String albumVal = albumEdit.getText().trim();
        final String yearVal = yearEdit.getText().trim();

        Song editedSong = new Song(songVal, artistVal);
        final Boolean shouldCheckUnique = !origSong.song.equalsIgnoreCase(editedSong.song)
                && !origSong.artist.equalsIgnoreCase(editedSong.artist);

        if (this.isValidSong(editedSong, shouldCheckUnique, albumVal, yearVal)) {
            this.storeManager.update(origSong, editedSong);
            this.showAlert("edit success");
            songEdit.clear();
            artistEdit.clear();
            albumEdit.clear();
            yearEdit.clear();
            this.loadSongs();
            table.getSelectionModel().select(editedSong);
        }

    }

    /**
     * Utilities
     */
    private void loadSongs() {
        table.setItems(this.storeManager.getSongList());
        table.getSortOrder().add(songCol);
        table.getSortOrder().add(artistCol);

        final boolean isEmpty = table.getItems().size() < 1;
        editSubmit.setDisable(isEmpty);
        deleteSubmit.setDisable(isEmpty);
    }

    private boolean isNum(String year) {
        try {
            Integer.parseInt(year);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private Boolean isValidSong(Song song, Boolean unique, String albumVal, String yearVal) {
        if (song.song.isEmpty() || song.artist.isEmpty()) {
            this.showAlert("required");
            return false;
        }
        if (unique && !this.storeManager.isUnique(song)) {
            this.showAlert("unique");
            return false;
        }

        if (!albumVal.isEmpty())
            song.album = albumVal;
        if (!yearVal.isEmpty()) {
            if (isNum(yearVal)) {
                song.year = Integer.parseInt(yearVal);
            } else {
                this.showAlert("year");
                return false;
            }
        }
        return true;
    }

    private void showSelected(Song selected) {
        albumEdit.clear();
        yearEdit.clear();
        songEdit.setText(selected.song);
        artistEdit.setText(selected.artist);
        if (selected.album != null && !selected.album.equals("null"))
            albumEdit.setText(selected.album);
        if (selected.year != -1)
            yearEdit.setText(Integer.toString(selected.year));
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
        case "add success":
            alert.setText("Song added!");
            alert.setFill(Color.GREEN);
            break;
        case "edit success":
            alert.setText("Song edited!");
            alert.setFill(Color.GREEN);
            break;
        case "delete success":
            alert.setText("Song deleted!");
            alert.setFill(Color.GREEN);
            break;
        case "year":
            alert.setText("Year must be a number!");
            alert.setFill(Color.RED);
        }
        alert.setVisible(true);
    }

}