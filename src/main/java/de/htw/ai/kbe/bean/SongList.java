package de.htw.ai.kbe.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Entity-Klasse die eine Songliste repräsentiert
 * 
 * @author jns
 *
 */
@XmlRootElement(name = "songList")
@Entity
@Table(name = "`SongList`")
public class SongList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// One User und Many Songlisten
	// da owner ein komplexer Datentyp ist, erkennt Hibernate, dass es sich um die
	// bereits bekannte Entität SongRXUser handelt
	// Konvention: Verknüpft die Spalte SongList.user_id mit dem Primärschlüssel von
	// SongRXUser.id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private SongRXUser owner;

	@Column(name = "is_private")
	private boolean isPrivate;

	// Many Songlisten und Many Songs
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "`SongList_Song`",
		joinColumns = { @JoinColumn(name = "songlist_id", referencedColumnName = "id") },
		inverseJoinColumns = { @JoinColumn(name = "song_id", referencedColumnName = "id") })
	// Set, da keine Reihenfolge und jeder Song nur genau einmal darin enthalten
	private Set<Song> songList = new HashSet<>();

	/**
	 * leerer Standard-Konstruktor
	 */
	public SongList() {
	}
	
	// Builder
	public static class Builder {

		private Integer id;
		private SongRXUser owner;
		private boolean isPrivate;
		private Set<Song> songList;

		public Builder() {

		}

		public Builder id(Integer i) {
			id = i;
			return this;
		}

		public Builder owner(SongRXUser own) {

			owner = own;
			return this;
		}

		public Builder isPrivate(boolean isP) {

			isPrivate = isP;
			return this;
		}

		public Builder songList(Set<Song> songL) {

			songList = songL;
			return this;

		}


		public SongList build() {

			return new SongList(this);
		}

	}

	private SongList(Builder builder) {
		this.id = builder.id;
		this.owner = builder.owner;
		this.isPrivate = builder.isPrivate;
		this.songList = builder.songList;
	}

	/**
	 * Methode gibt die Id einer Songliste zurück
	 * 
	 * @return id der Songliste
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Methode setzt die Id einer Songliste
	 * 
	 * @param id
	 *            der Songliste
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public SongRXUser getOwner() {
		return owner;
	}

	public void setOwner(SongRXUser owner) {
		this.owner = owner;
	}

	public Set<Song> getSongList() {
		return songList;
	}

	public void setSongList(Set<Song> songList) {
		this.songList = songList;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

}
