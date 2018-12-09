package de.htw.ai.kbe.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Klasse die ein Song repräsentiert
 * 
 * @author jns
 *
 */
@XmlRootElement(name = "song")
// alternative zu setter Methoden: @XmlAccessorType(XmlAccessType.FIELD)
public class Song {

	private Integer id;
	private String title;
	private String artist;
	private String album;
	private Integer released;

	/**
	 * Konstruktor
	 */
	public Song() {
	}

	public static class Builder {

		private Integer id;
		private String title;
		private String artist;
		private String album;
		private Integer released;

		public Builder() {

		}

		public Builder id(Integer i) {

			id = i;
			return this;
		}

		public Builder title(String tit) {

			title = tit;
			return this;
		}

		public Builder artist(String art) {

			artist = art;
			return this;
		}

		public Builder album(String al) {

			album = al;
			return this;

		}

		public Builder released(Integer rel) {

			released = rel;
			return this;

		}

		public Song build() {

			return new Song(this);
		}

	}

	private Song(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.artist = builder.artist;
		this.album = builder.album;
		this.released = builder.released;
	}

	/**
	 * Methode gibt die Id eines Songs zurück
	 * 
	 * @return Song
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Methode setzt die Id eines Songs
	 * 
	 * @param id
	 *            des Songs
	 */
	public void setId(Integer id) {
		// muss Integer sein, statt nur int, da sonst get/set Methodenpaar nicht
		// gefunden wird für xml
		this.id = id;
	}

	/**
	 * Methode gibt den Titel eines Songs zurück
	 * 
	 * @return Songtitel
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Methode gibt den Künstlername eines Songs zurück
	 * 
	 * @return Künstlername
	 */
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Methode gibt das Album eines Songs zurück
	 * 
	 * @return Songalbum
	 */
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * Methode gibt das Veröffentlichungsdatum eines Songs zurück
	 * 
	 * @return Veröffentlichungsdatum
	 */
	public Integer getReleased() {
		return released;
	}

	public void setReleased(Integer released) {
		this.released = released;
	}

	public void updateFromSong(Song song) {
		// song wird bereits auf Ebene des Web Services geprüft bzgl song != null und
		// song.getTitle() != null
		this.setTitle(song.getTitle());
		this.setArtist(song.getArtist());
		this.setReleased(song.getReleased());
		this.setAlbum(song.getAlbum());
	}

	/**
	 * Methode gibt den Song im Stringformat zurück
	 * 
	 * @return String eines Songs
	 */
	@Override
	public String toString() {
		return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
				+ released + "]";
	}
}
