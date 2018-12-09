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

/**
 * Klasse um authentifizierte User zu speichern
 * @author camilo, jns
 *
 */
public class AuthDatabase implements IAuthDatabase {
	private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	private List<User> users = new ArrayList<>();
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public AuthDatabase() {
		load();
	}

	public synchronized static AuthDatabase getInstance() {
		return new AuthDatabase();
	}
	
	/**
	 * Methode lädt eine Liste von User Objekten aus einer json Datei in eine
	 * List<User>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();

		try {
			try {
				InputStream is = this.getClass().getClassLoader().getResourceAsStream("auth_database.json");
				List<User> userFromFile = (List<User>) mapper.readValue(is, new TypeReference<List<User>>() {
				});
								
				users.addAll(userFromFile);
			} catch (Exception e) {
				// Liste wäre leer
                System.out.println("It was not possible to read auth_database.json file, hence database is empty.");
				e.printStackTrace();
			}
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Methode um einen User aus der Datenbank zu bekommen
	 * @param identifizierender username des Users
	 * @return angefragten User
	 */
	@Override
	public User getUserById(String username) {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();
		
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

	/**
	 * Methode um alle User in der Datebank zu bekommen
	 * @return alle User
	 */
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

	/**
	 * Methode um einen token zu validieren
	 * @param zu validierender token
	 * @return wenn der token valide ist true, ansonsten false
	 */
	@Override
	public boolean isTokenValid(String token) {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();

		try {
			for (User user : users) {
				if (user.getToken().getTokenStr().equals(token)) {
					return true;
				}
			}

			// users ist entweder leer oder token nicht valide
			return false;

		} finally {
			readLock.unlock();
		}
	}
}
