package types;

/**
 * @author Ryan Brandt
 * @author Ian Barry
 */
public class Song {
    public String song, artist, album;
    public int year;

    public Song(String song, String artist, String album, int year) {
        this.song = song;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }

    public Song(String song, String artist) {
        this(song, artist, null, -1);
    }

    /**
     * Utilities
     */
    public String getSong() {
        return this.song;
    }

    public String getArtist() {
        return this.artist;
    }

    public String toString() {
        return this.song + "," + this.artist + "," + this.album + "," + Integer.toString(this.year);
    }

}