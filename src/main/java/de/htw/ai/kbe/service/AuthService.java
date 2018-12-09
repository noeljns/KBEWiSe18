package de.htw.ai.kbe.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htw.ai.kbe.storage.IAuthDatabase;
import de.htw.ai.kbe.user.User;

@Path("/auth")
/**
 * Klasse um zu prüfen, ob es dem Client der Anfrage erlaubt ist, einen token zu bekommen
 * @author camilo, jns
 *
 */
public class AuthService {

	private IAuthDatabase authDatabase;

	@Inject
	public AuthService(IAuthDatabase authDb) {
		super();
		this.authDatabase = authDb;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getAuthToken(@QueryParam("userId") String userId) {
		User user = authDatabase.getUserById(userId);
		// userId does not exist, hence client ist not authorized to get a token
		if (user == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}

		// userId does exist, hence client ist authorized to get a token
		String token = user.getToken().getTokenStr();
		return Response.ok(token).build();
	}
}
