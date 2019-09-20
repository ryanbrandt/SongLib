package store;

import java.io.*;

import types.Song;

public class StoreManager {
    private BufferedWriter writer;
    private BufferedReader reader;

    public StoreManager() {
        try {
            File file = new File("src/main/java/store/store.txt");
            BufferedWriter wt = new BufferedWriter(new FileWriter(file, true));
            BufferedReader rd = new BufferedReader(new FileReader(file));
            this.writer = wt;
            this.reader = rd;
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void put(Song song) {
        try {
            this.writer.write(song.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void update(Song song) {

    }

    public void delete(Song song) {

    }

    public static boolean isUnique(Song song) {
        return true;
    }
}