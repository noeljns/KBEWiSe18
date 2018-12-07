package de.htw.ai.kbe.di;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htw.ai.kbe.storage.AuthDatabase;
import de.htw.ai.kbe.storage.IAuthDatabase;
import de.htw.ai.kbe.storage.IDatabaseSongs;
import de.htw.ai.kbe.storage.InMemoryDatabaseSongs;

public class DependencyBinder extends AbstractBinder {
	@Override
	protected void configure() {
		System.out.println("start of configure method of DependencyBinder ");
		bind(InMemoryDatabaseSongs.class).to(IDatabaseSongs.class).in(Singleton.class);
		System.out.println("before binding auth database in configure method of DependencyBinder \n");
		
		bind(AuthDatabase.class).to(IAuthDatabase.class).in(Singleton.class);
		// von Flo teilweise: bind(AuthDatabase.getInstance()).to(IAuthDatabase.class);
				
	}
}