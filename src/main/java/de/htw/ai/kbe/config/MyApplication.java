package de.htw.ai.kbe.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import de.htw.ai.kbe.di.DependencyBinder;

@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {
	
		public MyApplication() {
			register(new DependencyBinder());
			packages("de.htw.ai.kbe.service");
		}
	
}
		