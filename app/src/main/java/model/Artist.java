package model;

public class Artist {
    private int id;
    private String nameArtist;
    private String numberAlbum;
    private String numberSong;

    public Artist(int id, String nameArtist, String numberAlbum, String numberSong) {
        this.id = id;
        this.nameArtist = nameArtist;
        this.numberAlbum = numberAlbum;
        this.numberSong = numberSong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public String getNumberAlbum() {
        return numberAlbum;
    }

    public void setNumberAlbum(String numberAlbum) {
        this.numberAlbum = numberAlbum;
    }

    public String getNumberSong() {
        return numberSong;
    }

    public void setNumberSong(String numberSong) {
        this.numberSong = numberSong;
    }
}
