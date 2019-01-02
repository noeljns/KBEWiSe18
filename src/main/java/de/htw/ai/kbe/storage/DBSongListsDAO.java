package de.htw.ai.kbe.storage;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.SongList;

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

	@Override
	public SongList getSongListById(Integer id) {
		EntityManager em = emf.createEntityManager();

		try {
			return em.find(SongList.class, id);
		} finally {
			em.close();
		}
	}

	@Override
	public List<SongList> getAllSongLists() {

		EntityManager em = emf.createEntityManager();

		try {
			TypedQuery<SongList> query = em.createQuery("SELECT s FROM SongLists s ORDER BY s.id", SongList.class);
			return query.getResultList();
		} finally {
			em.close();
		}

	}

	@Override
	public Integer addSongList(SongList songList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSongList(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isIdInDatabase(Integer id) {

		return getSongListById(id) != null;
	}

	@Override
	public boolean isPublicById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
