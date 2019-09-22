package store;

import java.io.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import types.Song;

/**
 * @author Ryan Brandt
 */
public class StoreManager {
    private BufferedWriter writer;
    private BufferedReader reader;
    private File store;

    public StoreManager() {
        this.store = new File("src/main/java/store/store.txt");
    }

    public void put(Song song) {
        try {
            this.writer = new BufferedWriter(new FileWriter(this.store, true));
            this.writer.write(song.toString());
            this.writer.append('\n');
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            this.close(true);
        }
    }

    public void update(Song song) {
        // TODO find and update a song
		//test
    }

    public void delete(Song song) {
        // TODO find and delete a song
    }

    public ObservableList<Song> getSongList() {
        ObservableList<Song> songs = FXCollections.observableArrayList();
        try {
            this.reader = new BufferedReader(new FileReader(this.store));
            String storeLine;
            String[] storeLineArr;
            while ((storeLine = this.reader.readLine()) != null) {
                storeLineArr = storeLine.split(",");
                songs.add(
                        new Song(storeLineArr[0], storeLineArr[1], storeLineArr[2], Integer.parseInt(storeLineArr[3])));
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            this.close(false);
        }
        return songs;
    }

    public boolean isUnique(Song song) {
        try {
            this.reader = new BufferedReader(new FileReader(this.store));
            String storeLine;
            String[] storeLineArr;
            while ((storeLine = this.reader.readLine()) != null) {
                storeLineArr = storeLine.split(",");
                if (storeLineArr[0].equals(song.song) && storeLineArr[1].equals(song.artist)) {
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            this.close(false);
        }
        return true;
    }

    /**
     * Utilities
     */
    private void close(Boolean writer) {
        try {
            if (writer) {
                this.writer.close();
            } else {
                this.reader.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}