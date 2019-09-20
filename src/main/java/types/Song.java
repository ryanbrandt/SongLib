package types;

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

    public String toString() {
        return this.song + "," + this.artist + "," + this.album + "," + Integer.toString(this.year) + ";";
    }

    public String[] toArray() {
        return new String[] { this.song, this.artist, this.album != null ? this.album : "",
                this.year > -1 ? Integer.toString(this.year) : "" };
    }

}