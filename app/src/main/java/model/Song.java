package model;

public class Song {
    private int id;
    private String nameSong;
    private String nameArtist ;
    private int imageSong;

    public Song(int id, String nameSong, String nameArtist, int imageSong) {
        this.id = id;
        this.nameSong = nameSong;
        this.nameArtist = nameArtist;
        this.imageSong = imageSong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public int getImageSong() {
        return imageSong;
    }

    public void setImageSong(int imageSong) {
        this.imageSong = imageSong;
    }
}
