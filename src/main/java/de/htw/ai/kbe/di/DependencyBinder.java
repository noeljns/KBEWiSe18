package de.htw.ai.kbe.di;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htw.ai.kbe.storage.SongsDAO;
import de.htw.ai.kbe.storage.AuthDAO;
import de.htw.ai.kbe.storage.DBAuthDAO;
import de.htw.ai.kbe.storage.DBSongListsDAO;
import de.htw.ai.kbe.storage.DBSongsDAO;
import de.htw.ai.kbe.storage.SongListsDAO;

/**
 * Klasse die klärt, welche Implementierung der Datenbanken jeweils genutzt werden sollen
 * @author camilo, jns
 *
 */
public class DependencyBinder extends AbstractBinder {
	@Override
	protected void configure() {
		// nicht mehr notwendig: bind(Persistence.createEntityManagerFactory("songsDB-PU")).to(EntityManagerFactory.class);
		
		// unsere Factory für die EntityManagerFactory baut nun die Instanz
		// sorgt auch dafür, dass Verbindung zu PostgreSQL DB wieder sauber geschlossen wird
		// source: https://stackoverflow.com/questions/28045019/how-do-i-properly-configure-an-entitymanager-in-a-jersey-hk2-application
		bindFactory(EMFFactory.class).to(EntityManagerFactory.class).in(Singleton.class);

		bind(DBSongsDAO.class).to(SongsDAO.class).in(Singleton.class);
		bind(DBAuthDAO.class).to(AuthDAO.class).in(Singleton.class);
		bind(DBSongListsDAO.class).to(SongListsDAO.class).in(Singleton.class);
	}
}