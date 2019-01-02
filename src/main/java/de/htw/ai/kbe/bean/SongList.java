package de.htw.ai.kbe.bean;

import java.util.List;

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


@Entity
@Table(name = "`SongLists`") 
public class SongList {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne(targetEntity= SongRXUser.class)
	@JoinColumn(name="user_id")
	private SongRXUser owner;
	@Column(name = "isprivate")
	private boolean isPrivate;
	//@Column(name = "songlist")
	//TODO Dynamischer Datentyp? Wo wird diese Array initialisiert?
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SongList_Song",joinColumns= {@JoinColumn( name = "songList_id", referencedColumnName= "id")},inverseJoinColumns= {@JoinColumn( name = "song_id", referencedColumnName= "id")})
	List<Song> songList;	

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

//	public Integer[] getSongList() {
//		return songList;
//	}
//
//	public void setSongList(Integer[] songList) {
//		this.songList = songList;
//	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

}
