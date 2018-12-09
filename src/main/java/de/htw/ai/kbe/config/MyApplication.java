package de.htw.ai.kbe.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import de.htw.ai.kbe.di.DependencyBinder;

/**
 * Jersey spezifische Klasse, um den Dependecy Binder zu registrieren
 * @author camilo, jns
 *
 */
@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {

	public MyApplication() {
		register(new DependencyBinder());
		packages("de.htw.ai.kbe.service");
	}
}
