package de.htw.ai.kbe.storage;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import de.htw.ai.kbe.bean.SongList;

/**
 * Klasse, um Songlisten Objekte zu laden und zu verwalten
 * 
 * @author jns, camilo
 *
 */
public class DBSongListsDAO implements SongListsDAO {

	private EntityManagerFactory emf;

	/**
	 * Konstruktor
	 * 
	 * @param EntityManagerFactory
	 */
	@Inject
	public DBSongListsDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Methode um eine Songliste mit einer bestimmten id zu bekommen
	 */
	@Override
	public SongList getSongListById(Integer id) {
		EntityManager em = emf.createEntityManager();

		try {
			return em.find(SongList.class, id);
		} finally {
			em.close();
		}
	}

	/**
	 * Methode um die privaten als auch öffentlichen Songlisten eines users zu bekommen
	 */
	@Override
	public List<SongList> getAllSongLists(String username) {
		EntityManager em = emf.createEntityManager();

		try {
			TypedQuery<SongList> query = em.createQuery("SELECT s FROM SongList s WHERE s.owner.username = :username ORDER BY s.id", SongList.class)
					.setParameter("username", username);;
			return query.getResultList();
		} finally {
			em.close();
		}

	}

	/**
	 * Methode um die öffentlichen Songlisten eines users zu bekommen
	 */
	@Override
	public List<SongList> getAllPublicSongLists(String username) {
		EntityManager em = emf.createEntityManager();

		try {
			TypedQuery<SongList> query = em.createQuery("SELECT s FROM SongList s WHERE s.owner.username = :username AND s.isPrivate = false ORDER BY s.id", SongList.class)
					.setParameter("username", username);;
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	/**
	 * Methode um eine Songliste dem User mmuster hinzuzufügen
	 */
	@Override
	public Integer addSongList(SongList songList) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Methode um eine Songliste zu löschen
	 */
	@Override
	public void deleteSongList(Integer id) {
		// TODO Auto-generated method stub

	}

	/**
	 * Methode die prüft, ob Datenbank die id enthält
	 * @return falls die id in der Datenbank existiert true, ansonsten false
	 */
	@Override
	public boolean isIdInDatabase(Integer id) {
		return getSongListById(id) != null;
	}


}
