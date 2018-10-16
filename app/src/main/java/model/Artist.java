package model;

public class Artist {
    private int id;
    private String numberAlbum;
    private String nameArtist;
    private String numberSong;
    private int imageArtist;

    public Artist(int id, String numberAlbum, String nameArtist, String numberSong) {
        this.id = id;
        this.numberAlbum = numberAlbum;
        this.nameArtist = nameArtist;
        this.numberSong = numberSong;
    }

    public Artist(int id, String numberAlbum, String nameArtist, String numberSong, int imageArtist) {
        this.id = id;
        this.numberAlbum = numberAlbum;
        this.nameArtist = nameArtist;
        this.numberSong = numberSong;
        this.imageArtist = imageArtist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberAlbum() {
        return numberAlbum;
    }

    public void setNumberAlbum(String numberAlbum) {
        this.numberAlbum = numberAlbum;
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public String getNumberSong() {
        return numberSong;
    }

    public void setNumberSong(String numberSong) {
        this.numberSong = numberSong;
    }

    public int getImageArtist() {
        return imageArtist;
    }

    public void setImageArtist(int imageArtist) {
        this.imageArtist = imageArtist;
    }
}
