package de.htw.ai.kbe.songsServlet;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.service.SongWebService;
import de.htw.ai.kbe.storage.IDatabaseSongs;
import de.htw.ai.kbe.storage.InMemoryDatabaseSongs;

public class SongWebServiceTest extends JerseyTest {
	
	
	@Override
	protected Application configure() {
		return new ResourceConfig(SongWebService.class).register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind( InMemoryDatabaseSongs.class).to(IDatabaseSongs.class);
			}
		});
	}
	
	
	@Test
	public void givenNoSongIDReturnsOK() {
	    Response response = target("/songs/").request()
	        .get();
	    
	    
	 
	    assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());	    
	    
	}
	
	@Test
	public void givenIDReturnsCorrectSong() {
		
		 Song response = target("/songs/13").request()
			        .get(Song.class);
		 assertEquals(13, (int)response.getId());
	}
	
	
	
	
	
	
	
	
	
	
}