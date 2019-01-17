package de.htw.ai.kbe.storage;

import java.util.List;

import de.htw.ai.kbe.bean.SongList;

public interface SongListsDAO {
	public SongList getSongListById(Integer id);
	public List<SongList> getAllSongLists(String username);
	public List<SongList> getAllPublicSongLists(String username);
	public Integer addSongList(SongList songList);
	public void deleteSongList(Integer id);
	public boolean isIdInDatabase(Integer id);

}
