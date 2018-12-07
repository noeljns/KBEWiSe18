package de.htw.ai.kbe.storage;

import java.util.List;

import de.htw.ai.kbe.bean.User;

public interface IAuthDatabase {
	
	public User getUserById(String userid);	
	public List<User> getUsers();
	public int[] getIds();
	public boolean isTokenValid(String token);
	public boolean isTokenValidForUser(String userid, String token);
}
