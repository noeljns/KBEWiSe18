package de.htw.ai.kbe.songsServlet;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletResponse;
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
				bind( InMemoryTestDatabaseSongs.class).to(IDatabaseSongs.class);
			}
		});
	}


	@Test
	public void givenExistingIDReturnsCorrectSong() {
		
		 Song response = target("/songs/2").request()
			        .get(Song.class);
		 assertEquals(2, (int)response.getId());
	}
		
	 
	@Test
	public void givenStringAsIdReturnsNotFound() {
		
		 Response response = target("/songs/notanumber").request()
			        .get();
		 assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		 System.out.println("first response:" + response.toString());
	}
	
	
	//Fall3: abgefragte id existiert nicht (Fehlermeldung. id existiert nicht= schlechter Request= 400)

//	@Test
//	public void givenNonExistingIdReturnsNotFound() {
//		
//		 Response response = target("/songs/250").request()
//			        .get();
//		 //assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
//		 
//		 System.out.println(response.toString());
//		 
//		 //assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
//	}
//	
	
	
	
	
}