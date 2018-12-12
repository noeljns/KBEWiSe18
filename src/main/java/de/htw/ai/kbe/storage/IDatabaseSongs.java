package de.htw.ai.kbe.storage;

import java.util.List;

import de.htw.ai.kbe.bean.Song;

public interface IDatabaseSongs {
	
	public Song getSongById(Integer id);
	public List<Song> getAllSongs();
	public Integer addSong(Song song);
	public void deleteSong(Integer id);
	public void updateSong(Song song);
	public boolean isIdInDatabase(Integer id);
	public void save();
}
