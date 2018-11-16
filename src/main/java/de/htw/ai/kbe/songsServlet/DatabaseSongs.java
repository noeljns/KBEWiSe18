package de.htw.ai.kbe.songsServlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Klasse, um Songs Objecte in Json Format zu speichern
 * 
 * @author jns, camilo
 *
 */
public class DatabaseSongs {
	List<Song> songs;
	String databaseFileName;
	ReentrantLock dbAccessLock;
	int songIdCounter = 0;

	/**
	 * Konstruktor
	 * 
	 * @param databaseFileName
	 */
	public DatabaseSongs(String databaseFileName) {
		this.databaseFileName = databaseFileName;
		assert (this.databaseFileName != null);
		this.dbAccessLock = new ReentrantLock();
		load();
	}

	/**
	 * laded eine Liste von Song Objekten aus einer json Datei in eine List<Song>
	 * 
	 * @param databaseFileName
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		this.dbAccessLock.lock();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			try (InputStream is = new BufferedInputStream(new FileInputStream(this.databaseFileName))) {
				songs = (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			this.dbAccessLock.unlock();
		}
	}

	/**
	 * ladet eine List<Song> in eine json Datei
	 */
	public void save() {
		this.dbAccessLock.lock();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			try (OutputStream os = new BufferedOutputStream(new FileOutputStream(this.databaseFileName))) {
				objectMapper.writeValue(os, songs);
				System.out.println("Speicherung des Inhalts der Datenbank in " + databaseFileName
						+ " in Json Format war erfolgreich.");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			this.dbAccessLock.unlock();
		}
	}

	/**
	 * Methode, die alle Song Objekte in der Datenbank als Liste zurückgibt
	 * @return
	 */
	public List<Song> getSongs() {
		this.dbAccessLock.lock();
		try {
			return this.songs;
		} finally {
			this.dbAccessLock.unlock();
		}
	}
	
	/**
	 * Methode, die ein Song Objekt mit einer bestimmten ID aus der Datenbank zurückgibt
	 * @return Song Objekt mit bestimmter ID, falls es exisitiert, ansonsten null
	 */
    public Song getSongById(int id) {
    		System.out.println("in database getSongById");
    	
    		this.dbAccessLock.lock();
        try {
            int counter = 0;
            while (counter < this.songs.size()) {
                if (this.songs.get(counter).getId() == id)
                    return this.songs.get(counter);
                counter++;
            }
            return null;
        } finally {
            this.dbAccessLock.unlock();
        }
    }

	
}
