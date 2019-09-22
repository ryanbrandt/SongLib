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

    public void update(Song oldSong, Song newSong) {
        

        File temp = new File("src/main/java/store/temp.txt");

        try {

            this.writer = new BufferedWriter(new FileWriter(temp, false));
            this.reader = new BufferedReader(new FileReader(store));

            String storeLine;
            String[] storeLineArr;

            //make edit in temp
            while((storeLine = reader.readLine()) != null){
                storeLineArr = storeLine.split(",");

                String song  =  storeLineArr[0];
                String artist  =  storeLineArr[1];

                if(song.equalsIgnoreCase(oldSong.getSong()) && artist.equalsIgnoreCase(oldSong.getArtist())){
                    storeLine = newSong.toString();
                }
                this.writer.write(storeLine+"\n");
            }

            this.close(true);
            this.close(false);


            //copy temp to store
            this.writer = new BufferedWriter(new FileWriter(store, false));
            this.reader = new BufferedReader(new FileReader(temp));

            String copyLine;

            while((copyLine = reader.readLine()) != null){
                this.writer.write(copyLine+"\n");
            }

        } catch(IOException e ){
            System.out.println(e);
        } finally {
            this.close(true);
            this.close(false);

            temp.delete();
        }
    }

    public void delete(Song song) {
        File temp = new File("src/main/java/store/temp.txt");

        try {

            this.writer = new BufferedWriter(new FileWriter(temp, false));
            this.reader = new BufferedReader(new FileReader(store));

            String storeLine;
            String[] storeLineArr;

            //copy all but the song to be deleted over to temp
            while((storeLine = reader.readLine()) != null){
                storeLineArr = storeLine.split(",");

                String songCheck  =  storeLineArr[0];
                String artist  =  storeLineArr[1];

                if(songCheck.equalsIgnoreCase(song.getSong()) && artist.equalsIgnoreCase(song.getArtist())){

                } else {
                    this.writer.write(storeLine + "\n");
                }
            }

            this.close(true);
            this.close(false);


            //copy temp to store
            this.writer = new BufferedWriter(new FileWriter(store, false));
            this.reader = new BufferedReader(new FileReader(temp));

            String copyLine;

            while((copyLine = reader.readLine()) != null){
                this.writer.write(copyLine+"\n");
            }

        } catch(IOException e ){
            System.out.println(e);
        } finally {
            this.close(true);
            this.close(false);
            
            temp.delete();
        }
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