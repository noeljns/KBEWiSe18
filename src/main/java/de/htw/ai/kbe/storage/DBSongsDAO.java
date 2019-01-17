package de.htw.ai.kbe.storage;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htw.ai.kbe.bean.Song;

/**
 * Klasse, um Song Objekte zu laden und zu verwalten
 * 
 * @author jns, camilo
 *
 */
public class DBSongsDAO implements SongsDAO {

	private EntityManagerFactory emf;

	/**
	 * Konstruktor
	 * 
	 * @param EntityManagerFactory
	 */
	@Inject
	public DBSongsDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Methode, die alle Song Objekte in der Datenbank als Liste zurückgibt
	 * 
	 * @return Liste aller Songs
	 */
	public List<Song> getAllSongs() {
		EntityManager em = emf.createEntityManager();

		try {
			TypedQuery<Song> query = em.createQuery("SELECT s FROM Song s ORDER BY s.id", Song.class);
			return query.getResultList();
		} finally {
			em.close();
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
		EntityManager em = emf.createEntityManager();

		try {
			return em.find(Song.class, id);
		} finally {
			em.close();
		}
	}

	/**
	 * Methode um der Datenbank einen Song hinzuzufügen
	 * 
	 * @param Song Objekt das zu der Datenbank hinzugefügt werden soll
	 */
	public Integer addSong(Song song) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(song);
			transaction.commit();
			return song.getId();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding song: " + e.getMessage());
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
		return getSongById(id) != null;
	}

	@Override
	public void save() {		
	}
}
