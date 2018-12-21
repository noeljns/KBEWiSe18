package de.htw.ai.kbe.storage;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.htw.ai.kbe.bean.Song;

/**
 * Klasse, um Song Objekte aus einer Json Datei zu laden und zu verwalten
 * 
 * @author jns, camilo
 *
 */
public class DBSongsDAO implements SongsDAO {

	private EntityManagerFactory emf;

	/**
	 * Konstruktor
	 * 
	 * @param databaseFileName
	 */
	@Inject
	public DBSongsDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

//	public static DBSongsDAO getInstance() {
//		return new DBSongsDAO();
//	}

	/**
	 * Methode lädt eine Liste von Song Objekten aus einer json Datei in eine
	 * List<Song>
	 * 
	 */
	public void load() {

	}

	/**
	 * Methode lädt eine List<Song> in eine json Datei
	 */
	public void save() {

	}

	/**
	 * Methode, die alle Song Objekte in der Datenbank als Liste zurückgibt
	 * 
	 * @return Liste aller Songs
	 */
	public List<Song> getAllSongs() {

		EntityManager em = emf.createEntityManager();
		List<Song> songs = new ArrayList<>();

		try {
			TypedQuery<Song> query = em.createQuery("SELECT s FROM Song s", Song.class);
			songs = query.getResultList();
		} finally {
			em.close();
		}
		return new ArrayList<Song>(songs);
	}

	/**
	 * Methode, die ein Song Objekt mit einer bestimmten ID aus der Datenbank
	 * zurückgibt
	 * 
	 * @param Id des angeforderten Songs
	 * @return Song Objekt mit bestimmter ID falls es exisitiert, ansonsten null
	 */
	public Song getSongById(Integer id) {
		EntityManager em = emf.createEntityManager();
		Song entity = null;
		try {
			entity = em.find(Song.class, id);
		} finally {
			em.close();
		}
		return entity;

	}

	/**
	 * Methode um der Datenbank einen Song hinzuzufügen
	 * 
	 * @param Song Objekt das zu der Datenbank hinzugefügt werden soll
	 */
	public Integer addSong(Song song) {
		// override hat keine bedeutung !!!
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(song);
			transaction.commit();
			return song.getId();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding contact: " + e.getMessage());
			transaction.rollback();
			throw new PersistenceException("Could not persist entity: " + e.toString());
		} finally {
			em.close();
		}

	}

	/**
	 * Methode um Song Objekt zu löschen
	 * 
	 * @param Id des zu löschenden Songs
	 */
	public void deleteSong(Integer id) {

	}

	/**
	 * Methode um Song Objekt zu bearbeiten
	 * 
	 * @param zu bearbeitendes Song Objekt
	 */
	public void updateSong(Song song) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			Song dbSong = em.find(Song.class, song.getId());
			if (dbSong != null) {
				dbSong.updateFromSong(song);
			}
			transaction.commit();
		} finally {
			em.close();
		}

	}

	/**
	 * Methode um zu prüfen, ob Song Objekt in Datenbanke liegt
	 * 
	 * @param Id des zu überprüfenden Songs
	 * @return wenn das Song Objekt existiert true, ansonsten false
	 */
	public boolean isIdInDatabase(Integer id) {
		EntityManager em = emf.createEntityManager();
		boolean result = false;
		try {
			result = em.find(Song.class, id) != null;
		} finally {
			em.close();
		}
		return result;
	}
}
