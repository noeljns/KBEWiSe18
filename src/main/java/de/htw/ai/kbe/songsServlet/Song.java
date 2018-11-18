package de.htw.ai.kbe.songsServlet;

public class Song {

	private Integer id;
	private String title;
	private String artist;
	private String album;
	private Integer released;

	private Song() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public Integer getReleased() {
		return released;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
				+ released + "]";
	}
}
