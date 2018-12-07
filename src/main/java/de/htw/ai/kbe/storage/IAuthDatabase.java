package de.htw.ai.kbe.storage;

import java.util.List;

import de.htw.ai.kbe.user.User;

public interface IAuthDatabase {
	
	public User getUserById(String userid);	
	public List<User> getUsers();
	public boolean isTokenValid(String token);
}
