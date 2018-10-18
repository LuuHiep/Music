package model;

public class Album {
    private int id;
    private String nameAlbums;
    private String nameAuthor;
    private String imageAlbum;

    public Album(int id, String nameAlbums, String nameAuthor, String imageAlbum) {
        this.id = id;
        this.nameAlbums = nameAlbums;
        this.nameAuthor = nameAuthor;
        this.imageAlbum = imageAlbum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAlbums() {
        return nameAlbums;
    }

    public void setNameAlbums(String nameAlbums) {
        this.nameAlbums = nameAlbums;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getImageAlbum() {
        return imageAlbum;
    }

    public void setImageAlbum(String imageAlbum) {
        this.imageAlbum = imageAlbum;
    }
}
