package model;

public class Song {
    private int id;
    private String title;
    private String album;
    private String artist;
    private int duration;
    private int imageSong;
    private String path;

    public Song(int id, String title, String album, String artist, int duration, int imageSong, String path) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.imageSong = imageSong;
        this.path = path;
    }

    public Song(int id, String title, String artist, int imageSong) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.imageSong = imageSong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getImageSong() {
        return imageSong;
    }

    public void setImageSong(int imageSong) {
        this.imageSong = imageSong;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
