package de.htw.ai.kbe.storage;

import java.util.List;

import de.htw.ai.kbe.bean.Song;

public interface IDatabaseSongs {


	public Song getSongById(int id);
	public List<Song> getAllSongs();
	public void addSong(Song song);
	//todo: update and delete



}
