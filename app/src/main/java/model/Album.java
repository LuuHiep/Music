package model;

public class Album {
    private int id;
    private String title;
    private String artist;
    private String imageAlbum;

    public Album(int id, String title, String artist, String imageAlbum) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.imageAlbum = imageAlbum;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageAlbum() {
        return imageAlbum;
    }

    public void setImageAlbum(String imageAlbum) {
        this.imageAlbum = imageAlbum;
    }
}
