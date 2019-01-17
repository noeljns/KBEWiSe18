package de.htw.ai.kbe.di;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.api.Factory;

/**
 * Factory Klasse für eine Entity Manager Factory
 * 
 * @author camilo, jns
 *
 */
public class EMFFactory implements Factory<EntityManagerFactory> {
	// source: https://stackoverflow.com/questions/28045019/how-do-i-properly-configure-an-entitymanager-in-a-jersey-hk2-application
	// sorgt dafür, dass die Verbindung zur PostgreSQL Datenbank nach getaner Arbeit auch wieder geschlossen wird
	// ansonsten  bleiben werden die Prozesse erst nach einem von der DB bestimmten timeout beendet
	// die DB erlaubt aber nur 10 offene Verbindungen und blockiert daraufhin alle weiteren Anfragen
	// dies führt dann zu der IllegalStateException / dem Scheitern der Registrierung von Hibernate bei der DB
	
    private static final String PERSISTENCE_UNIT = "songsDB-PU";

    public EMFFactory() {
    }

    @Override
    public EntityManagerFactory provide() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    // sorgt dafür, dass Verindung zur DB sauber getrennt wird
    @Override
    public void dispose(EntityManagerFactory emf) {
        if(emf.isOpen()) {
        	emf.close();
        }
    }
}