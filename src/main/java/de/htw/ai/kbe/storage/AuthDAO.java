package de.htw.ai.kbe.storage;

import java.util.List;

import de.htw.ai.kbe.user.User;

public interface AuthDAO {
	
	public User getUserById(String userid);	
	public List<User> getUsers();
	public boolean isTokenValid(String token);
}
