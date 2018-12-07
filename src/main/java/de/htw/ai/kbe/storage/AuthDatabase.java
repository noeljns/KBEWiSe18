package de.htw.ai.kbe.storage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.htw.ai.kbe.user.User;

public class AuthDatabase implements IAuthDatabase {
	private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	private List<User> users = new ArrayList<>();
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public AuthDatabase() {
		load();
	}

	/**
	 * Methode lädt eine Liste von User Objekten aus einer json Datei in eine
	 * List<User>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		System.out.println("the user database is loaded");
		System.out.println("the user database is loaded");
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();

		try {
			try {
				InputStream is = this.getClass().getClassLoader().getResourceAsStream("auth_database.json");
				List<User> userFromFile = (List<User>) mapper.readValue(is, new TypeReference<List<User>>() {
				});
				
				// Test
				if(userFromFile.isEmpty()) {
					System.out.println("Unsere userFromFile Liste ist leer!");
				}
				
				// Test
				for (User user : userFromFile) {
					System.out.println("userId: " + user.getUserId());
				}
				
				users.addAll(userFromFile);
			} catch (Exception e) {
				// Liste wäre leer
				e.printStackTrace();
			}
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public User getUserById(String username) {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();

		// Test
		if(users.isEmpty()) {
			System.out.println("Unsere users Liste ist leer!");
		}
		
		// Test
		for (User user : users) {
			System.out.println("userId: " + user.getUserId());
		}
		
		try {
			for (User user : users) {
				if (user.getUserId().equals(username)) {
					return user;
				}
			}

			// users ist entweder leer oder userid nicht existent
			return null;

		} finally {
			readLock.unlock();
		}
	}

	@Override
	public List<User> getUsers() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();

		try {
			return this.users;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int[] getIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTokenValid(String token) {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();

		try {
			for (User user : users) {
				if (user.getToken().equals(token)) {
					return true;
				}
			}

			// users ist entweder leer oder token nicht valide
			return false;

		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean isTokenValidForUser(String userid, String token) {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();

		try {
			for (User user : users) {
				if (user.getUserId().equals(userid)) {
					if (user.getToken().equals(token)) {
						return true;
					}

				}
			}

			return false;

		} finally {
			readLock.unlock();
		}

	}

}
