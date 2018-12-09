package de.htw.ai.kbe.songsServlet;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.storage.IDatabaseSongs;

/**
 * Test Klasse, um Song Objekte in Json Format zu speichern
 * 
 * @author jns, camilo
 *
 */
public class InMemoryTestDatabaseSongs implements IDatabaseSongs {

	private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	private List<Song> songs = new ArrayList<>();
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private String databaseFileName;

	/**
	 * Konstruktor
	 * 
	 * @param databaseFileName
	 */
	public InMemoryTestDatabaseSongs() {
		load();
	}

	public static InMemoryTestDatabaseSongs getInstance() {
		return new InMemoryTestDatabaseSongs();
	}

	/**
	 * Methode lädt eine Liste von Song Objekten aus einer json Datei in eine
	 * List<Song>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();

		try {
			try {
				InputStream is = this.getClass().getClassLoader().getResourceAsStream("songs_test.json");
				List<Song> songsFromFile = (List<Song>) mapper.readValue(is, new TypeReference<List<Song>>() {
				});
				songs.addAll(songsFromFile);
			} catch (Exception e) {
				// Liste wäre leer
                System.out.println("It was not possible to read songs_test.json file, hence database is empty.");
				e.printStackTrace();
			}
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Methode lädt eine List<Song> in eine json Datei
	 */
	public void save() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();

		try {
			try (OutputStream os = new BufferedOutputStream(new FileOutputStream(this.databaseFileName))) {
				mapper.writeValue(os, songs);
				System.out.println("Speicherung des Inhalts der Datenbank in " + databaseFileName
						+ " in Json Format war erfolgreich.");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Methode, die alle Song Objekte in der Datenbank als Liste zurückgibt
	 * 
	 * @return Liste aller Songs
	 */
	public List<Song> getAllSongs() {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();

		try {
			return this.songs;
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Methode, die ein Song Objekt mit einer bestimmten ID aus der Datenbank
	 * zurückgibt
	 * 
	 * @param Id des angeforderten Songs
	 * @return Song Objekt mit bestimmter ID falls es exisitiert, ansonsten null
	 */
	public Song getSongById(Integer id) {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();

		try {
			if (id > 0 && id <= songs.size()) {
				return songs.get(id - 1);
			} else {
				return null;
			}
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Methode um der Datenbank einen Song hinzuzufügen
	 * 
	 * @param Song Objekt das zu der Datenbank hinzugefügt werden soll
	 */
	public Integer addSong(Song song) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();

		try {
			// songs.size() ist automatisch letzter Index + 1 und damit letzte verwendete id
			// daher ist die nächste freie id für den neuen song size() + 1
			int nextId = songs.size() + 1;
			song.setId(nextId);
			songs.add(song);
			return song.getId();
		} finally {
			writeLock.unlock();
		}
	}

	// deletes song with given id from database and returns true if success,false
	// otherwise
	public void deleteSong(Integer id) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();

		try {
			songs.removeIf(song -> song.getId() == id);
		} finally {
			writeLock.unlock();
		}
	}

	public void updateSong(Song song) {
		Integer id = song.getId();
		Song songToUpdate = getSongById(id);
		songToUpdate.updateFromSong(song);
	}

	public boolean isIdInDatabase(Integer id) {
		for (Song song : songs) {
			if (id.equals(song.getId())) {
				return true;
			}
		}
		return false;

		// alternativ: return songs.stream().filter(song -> song.getId() ==
		// id).findAny().isPresent();
	}
}
