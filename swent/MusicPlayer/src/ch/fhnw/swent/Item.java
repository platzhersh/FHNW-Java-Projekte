package ch.fhnw.swent;

public class Item {
	private String title, album, artist;
	
	public Item(String title, String album, String Artist) {
		this.title = title;
		this.album = album;
		this.artist = artist;
	}
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getAlbum() { return album; }
	public void setAlbum(String album) { this.album = album; }
	public String getArtist() { return artist; }
	public void setArtist(String artist) { this.artist = artist; }
}
