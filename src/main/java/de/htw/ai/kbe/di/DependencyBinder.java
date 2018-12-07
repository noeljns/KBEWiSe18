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
		bind(InMemoryDatabaseSongs.class).to(IDatabaseSongs.class).in(Singleton.class);
		bind(AuthDatabase.class).to(IAuthDatabase.class).in(Singleton.class);
	}
}