package model;

public class Song {
    private int id;
    private String nameSong;
    private String nameArtist ;
    private String album;
    private int idAlbum;
    private int imageSong;

    public Song(int id, String nameSong, String nameArtist, int idAlbum) {
        this.id = id;
        this.nameSong = nameSong;
        this.nameArtist = nameArtist;
        this.album = album;
        this.idAlbum = idAlbum;
    }

    public Song(int id, String nameSong, String nameArtist, String album, int idAlbum, int imageSong) {
        this.id = id;
        this.nameSong = nameSong;
        this.nameArtist = nameArtist;
        this.album = album;
        this.idAlbum = idAlbum;
        this.imageSong = imageSong;
    }

    public int getImageSong() {
        return imageSong;
    }

    public void setImageSong(int imageSong) {
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }
}
