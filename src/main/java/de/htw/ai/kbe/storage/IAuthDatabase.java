package de.htw.ai.kbe.storage;

import java.util.List;

import de.htw.ai.kbe.user.SongRXUser;

public interface IAuthDatabase {
	
	public SongRXUser getUserById(String userid);	
	public List<SongRXUser> getUsers();
	public boolean isTokenValid(String token);
}
