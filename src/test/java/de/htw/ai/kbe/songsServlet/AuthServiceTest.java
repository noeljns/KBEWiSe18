package de.htw.ai.kbe.songsServlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import de.htw.ai.kbe.service.AuthService;
import de.htw.ai.kbe.service.RequestFilter;
import de.htw.ai.kbe.service.SongWebService;
import de.htw.ai.kbe.storage.IAuthDatabase;
import de.htw.ai.kbe.storage.IDatabaseSongs;

public class AuthServiceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(SongWebService.class, RequestFilter.class, AuthService.class)
				.register(new AbstractBinder() {
					@Override
					protected void configure() {
						bind(InMemoryTestDatabaseSongs.class).to(IDatabaseSongs.class);
						bind(TestAuthDatabase.class).to(IAuthDatabase.class);
					}
				});
	}

	@Test
	public void whenMaximSendsAuthRequestThenTokenIsSent() {
		Response response = target("/auth").queryParam("userId", "mmuster").request().get();
		String generatedToken = response.readEntity(String.class);
		assertNotNull(generatedToken);
	}

	@Test
	public void whenElenaSendsAuthRequestThenTokenIsSent() {
		Response response = target("/auth").queryParam("userId", "eschuler").request().get();
		String generatedToken = response.readEntity(String.class);
		assertNotNull(generatedToken);

	}

	@Test
	public void whenUnknownUserSendsAuthRequestThenForbidden() {
		Response response = target("/auth").queryParam("userId", "unknown").request().get();
		assertEquals(403, response.getStatus());
	}
	
	@Test
	public void whenUserSendsAuthRequestWithEmptyStringThenForbidden() {
		Response response = target("/auth").queryParam("userId", "").request().get();
		assertEquals(403, response.getStatus());
	}

	@Test
	public void whenUserSendsAuthRequestWithNullThenForbidden() {
		String nullString= null;
		Response response = target("/auth").queryParam("userId", nullString).request().get();
		assertEquals(403, response.getStatus());
	}
}
