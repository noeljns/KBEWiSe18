package de.htw.ai.kbe.storage;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import de.htw.ai.kbe.bean.SongRXUser;

/**
 * Klasse um authentifizierte SongRXUser zu speichern
 * @author camilo, jns
 *
 */
public class DBAuthDAO implements AuthDAO {
	
	private EntityManagerFactory emf;
	
	/**
	 * Konstruktor
	 * 
	 * @param EntityManagerFactory
	 */
	@Inject
	public DBAuthDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Methode um alle SongRXUser in der Datebank zu bekommen
	 * @return alle SongRXUser
	 */
	@Override
	public List<SongRXUser> getUsers() {
		EntityManager em = emf.createEntityManager();

		try {
			TypedQuery<SongRXUser> query = em.createQuery("SELECT u FROM SongRXUser u", SongRXUser.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	/**
	 * Methode um einen SongRXUser aus der Datenbank zu bekommen
	 * @param identifizierender username des Users
	 * @return bei Erfolg angefragten SongRXUser, ansonsten null
	 */
	@Override
	public SongRXUser getUserById(String username) {
		EntityManager em = emf.createEntityManager();

		try {
			TypedQuery<SongRXUser> query = em.createQuery("SELECT u FROM SongRXUser u WHERE u.username = :username", SongRXUser.class)
					.setParameter("username", username);
			// falls entity null, dann ist users entweder leer oder userid nicht existent
			return query.getSingleResult();
		} finally {
			em.close();
		}
	}

	/**
	 * Methode um einen token zu validieren
	 * @param zu validierender token
	 * @return wenn der token valide ist true, ansonsten false
	 */
	@Override
	public boolean isTokenValid(String token) {
		List<SongRXUser> users = getUsers();

		for (SongRXUser user : users) {
			if (user.getToken().getTokenStr().equals(token)) {
				return true;
			}
		}

		// users ist entweder leer oder token nicht valide
		return false;
	}
}
