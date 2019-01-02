package de.htw.ai.kbe.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "`SongLists`") 
public class SongList {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private SongRXUser owner;
	@Column(name = "isprivate")
	private boolean isPrivate;
	@Column(name = "songlist")
	private Integer[] songList;

	/**
	 * leerer Standard-Konstruktor
	 */
	public SongList() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SongRXUser getOwner() {
		return owner;
	}

	public void setOwner(SongRXUser owner) {
		this.owner = owner;
	}

	public Integer[] getSongList() {
		return songList;
	}

	public void setSongList(Integer[] songList) {
		this.songList = songList;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

}
