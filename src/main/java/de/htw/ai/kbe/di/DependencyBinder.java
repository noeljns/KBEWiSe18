package de.htw.ai.kbe.di;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htw.ai.kbe.storage.DBAuthDAO;
import de.htw.ai.kbe.storage.AuthDAO;
import de.htw.ai.kbe.storage.SongsDAO;
import de.htw.ai.kbe.storage.DBSongsDAO;

/**
 * Klasse die klärt, welche Implementierung der Datenbanken jeweils genutzt werden sollen
 * @author camilo, jns
 *
 */
public class DependencyBinder extends AbstractBinder {
	@Override
	protected void configure() {
		bind(Persistence.createEntityManagerFactory("songsDB-PU"))
		.to(EntityManagerFactory.class);
		
		bind(DBSongsDAO.class).to(SongsDAO.class).in(Singleton.class);
		//bind(DBAuthDAO.class).to(AuthDAO.class).in(Singleton.class);
	}
}