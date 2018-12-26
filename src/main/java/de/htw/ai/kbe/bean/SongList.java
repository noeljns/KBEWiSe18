package de.htw.ai.kbe.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class SongList {
	@Id
	private Integer id;
	private SongRXUser user;
	private List<Song> songList;
	private boolean isPrivate;
	
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

	public SongRXUser getUser() {
		return user;
	}

	public void setUser(SongRXUser user) {
		this.user = user;
	}

	public List<Song> getSongList() {
		return songList;
	}

	public void setSongList(List<Song> songList) {
		this.songList = songList;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

}
