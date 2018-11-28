package de.htw.ai.kbe.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {
	
		public MyApplication() {
			packages("de.htw.ai.kbe.service");
		}

	
	
	
}
