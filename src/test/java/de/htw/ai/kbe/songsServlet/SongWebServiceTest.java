package de.htw.ai.kbe.songsServlet;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.service.AuthService;
import de.htw.ai.kbe.service.RequestFilter;
import de.htw.ai.kbe.service.SongWebService;
import de.htw.ai.kbe.storage.AuthDAO;
import de.htw.ai.kbe.storage.SongsDAO;
import de.htw.ai.kbe.user.DebugToken;

public class SongWebServiceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(SongWebService.class,RequestFilter.class,AuthService.class).register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(InMemoryTestDatabaseSongs.class).to(SongsDAO.class);
				bind(TestAuthDatabase.class).to(AuthDAO.class);
			}
		});
	}
	
	@Test
	public void updateSongWithExistingIdInUrlAndPayloadReturns204() {
		// payload: id ist 3, title ist "Update Song...", alles andere ist null
		Song song = new Song();
		song.setId(3);
		song.setTitle("Update Song With Id 3");
		Entity<Song> songEntity = Entity.entity(song, MediaType.APPLICATION_JSON);
		int id = 3; 				// id in url ist 3
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(204, response.getStatus());
	}
	
	@Test
	public void updateSongWithExistingIdInUrlButDifferentPayloadReturns400() {
		// payload: id ist 8, title ist "Update Song...", alles andere ist null
		Song song = new Song();
		song.setId(8);
		song.setTitle("Update Song With Id 8");
		Entity<Song> songEntity = Entity.entity(song, MediaType.APPLICATION_JSON);
		int id = 3; 				// id in url ist 3
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(400, response.getStatus());
	}
	
	@Test
	public void updateSongWithNotExistingIdReturns400() {
		// payload: id ist 37, title ist "Update Song...", alles andere ist null
		Song song = new Song();
		song.setId(37);
		song.setTitle("Update Song With Id 37");
		Entity<Song> songEntity = Entity.entity(song, MediaType.APPLICATION_JSON);
		int id = 37; 				// id in url 37
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void updateSongsWithXmlButShouldSendJSONReturns400() {
		Entity songEntity = Entity.entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><song><id>4</id><title>Update mit XML</title></song>", MediaType.APPLICATION_JSON);
		int id = 4; 				// id in url 4
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(400, response.getStatus());;
	}
	
	@Test
	public void updateSongsWithJSONButShouldSendXmlReturns400() {
		Entity songEntity = Entity.entity("{\"id\": 4, \"title\": \"Update With Valid Json\"}", MediaType.APPLICATION_XML);
		int id = 4; 				// id in url 4
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(400, response.getStatus());;
	}
	
	
	@Test
	public void updateSongWithoutTitleReturns400() {
		// payload: id ist 3, title ist null
		Song song = new Song();
		song.setId(3);
		String title = null;
		song.setTitle(title);
		Entity<Song> songEntity = Entity.entity(song, MediaType.APPLICATION_JSON);
		int id = 3; 				// id in url 3
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(400, response.getStatus());
	}
	
	@Test
	public void updateSongWithValidJsonReturns204() {
		Entity songEntity = Entity.entity("{\"id\": 4, \"title\": \"Update With Valid Json\"}", MediaType.APPLICATION_JSON);
		int id = 4; 				// id in url 4
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(204, response.getStatus());
	}
	
	@Test
	public void updateSongWithInvalidJsonReturns400() {
		Entity songEntity = Entity.entity("Invalid json string.", MediaType.APPLICATION_JSON);
		int id = 3; 				// id in url 3
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(400, response.getStatus());
	}
	
	@Test
	public void updateSongWithValidXmlReturns204() {
		Entity songEntity = Entity.entity("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><song><id>4</id><title>Update mit XML</title></song>", MediaType.APPLICATION_XML);
		int id = 4; 				// id in url 4
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(204, response.getStatus());
	}
	
	@Test
	public void updateSongWithInvalidXmlReturns400() {
		Entity songEntity = Entity.entity("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><songs>Invalid xml</songs>", MediaType.APPLICATION_XML);
		int id = 3; 				// id in url 3
		
		Response response = target("/songs/" + id).request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(400, response.getStatus());
	}
	
	@Test
	public void updateSongWithoutIdInUrlReturns405() {
		// payload: id ist 3, title ist "Update Song...", alles andere ist null
		Song song = new Song();
		song.setId(3);
		String title = null;
		song.setTitle(title);
		Entity<Song> songEntity = Entity.entity(song, MediaType.APPLICATION_JSON);

		Response response = target("/songs/").request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(405, response.getStatus());
	}
	
	@Test
	public void updateSongWithStringAsIdInUrlReturns404() {
		// payload: id ist 3, title ist "Update Song...", alles andere ist null
		Song song = new Song();
		song.setId(3);
		String title = null;
		song.setTitle(title);
		Entity<Song> songEntity = Entity.entity(song, MediaType.APPLICATION_JSON);

		Response response = target("/songs/notanumber").request().header("Authorization", DebugToken.DEBUG_TOKEN).put(songEntity);
		Assert.assertEquals(404, response.getStatus());
	}

	

	@Test
	public void getSongWithExistingIdReturnsCorrectSong() {
		Song response = target("/songs/2").request().header("Authorization", DebugToken.DEBUG_TOKEN).get(Song.class);
		assertEquals(2, (int) response.getId());
	}

	@Test
	public void getSongWithStringAsIdReturnsNotFound() {
		Response response = target("/songs/notanumber").request().header("Authorization", DebugToken.DEBUG_TOKEN).get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
}