package de.htw.ai.kbe.songsServlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
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


/**
 * Klasse, um Song Objekte in Json Format zu speichern
 * 
 * @author jns, camilo
 *
 */
public class DatabaseSongs {

	private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	private List<Song> songs = new ArrayList<>();
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private String databaseFileName;

	/**
	 * Konstruktor
	 * 
	 * @param databaseFileName
	 */
	public DatabaseSongs(String databaseFileName) {
		this.databaseFileName = databaseFileName;
		assert (this.databaseFileName != null);
	}

	/**
	 * Methode lädt eine Liste von Song Objekten aus einer json Datei in eine List<Song>
	 * 
	 * @param databaseFileName
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();

		try {
			try (InputStream is = new BufferedInputStream(new FileInputStream(this.databaseFileName))) {
				List<Song> songsFromFile = (List<Song>) mapper.readValue(is, new TypeReference<List<Song>>() {});
				songs.addAll(songsFromFile);
			} catch (Exception e) {
				// Liste wäre leer
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
	public Song getSongById(int id) {
		Lock readLock = readWriteLock.readLock();
		readLock.lock();

		try {
			if (id > 0 && id <= songs.size()) {
				return songs.get(id - 1);
			}
			else {
				return null;
			}
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Methode um der Datenbank einen Song hinzuzufügen
	 * @param Song Objekt das zu der Datenbank hinzugefügt werden soll
	 */
	public void addSong(Song song) {
		Lock writeLock = readWriteLock.writeLock();
		writeLock.lock();
		
		try {
			// songs.size() ist automatisch letzter Index + 1 und damit letzte verwendete id
			// daher ist die nächste freie id für den neuen song size() + 1
			int nextId = songs.size() + 1;
			song.setId(nextId);
			songs.add(song);
		} finally {
			writeLock.unlock();
		}
	}
}
