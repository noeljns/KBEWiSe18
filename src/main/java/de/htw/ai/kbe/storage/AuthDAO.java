package de.htw.ai.kbe.storage;

import java.util.List;

import de.htw.ai.kbe.bean.SongRXUser;

public interface AuthDAO {
	
	public SongRXUser getUserById(String userid);	
	public List<SongRXUser> getUsers();
	public boolean isTokenValid(String token);
}
