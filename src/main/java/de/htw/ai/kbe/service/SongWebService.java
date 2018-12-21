package de.htw.ai.kbe.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.storage.SongsDAO;

/**
 * Klasse eines Webservices, der get, post, put und delete Anfragen von Song Objekten
 * bearbeitet
 * 
 * @author jns, camilo
 *
 */
// URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/songs
@Path("/songs")
// durch @Secure werden alle Methoden gefiltert von RequestFilter
//@Secure
public class SongWebService {

	@Context
	private HttpServletResponse response;

	private SongsDAO database;

	@Inject
	public SongWebService(SongsDAO dao) {
		this.database = dao;
	}

	/**
	 * Methode um einen Song herauszugeben
	 * @param id
	 * @return angefragten Song
	 */
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Song getSong(@PathParam("id") Integer id) {
		
		if (!database.isIdInDatabase(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

			try {
				response.flushBuffer();
				return null;
			} catch (Exception e) {
			}
		}

		Song song = database.getSongById(id);
		return song;
	}

	/**
	 * Methode um alle Songs herauszugeben
	 * @return alle Songs
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Song> getAllSongs() {
		List<Song> songList = database.getAllSongs();
		return songList;
	}

	/**
	 * Methode zur Bearbeitung einer Request zum Hinzufügen eines
	 * Songs
	 * 
	 * @return response
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createSong(@Context UriInfo uriInfo, Song song) {
		// Song darf nicht null sein
		if (song == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		// Song muss zwingend einen Titel enthalten!
		if (song.getTitle() == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Song must contain a title.").build();
		}

		int newId = database.addSong(song);
		System.out.println("new id: " + newId);
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		uriBuilder.path(Integer.toString(newId));
		return Response.created(uriBuilder.build()).build();
	}

	/**
	 * Methode zur Bearbeitung einer Request zum Bearbeiten eines
	 * Songs
	 * 
	 * @return response
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response updateSong(@PathParam("id") Integer id, Song song) {
		if (song == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}
		// Song muss zwingend einen Titel enthalten!
		if (song.getTitle() == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Song must contain a title.").build();
		}
		// wenn die id in unserer datenbank nicht vorhanden ist
		if (!database.isIdInDatabase(id)) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Song with the provided id does not exist.").build();
		}
		// id in url ist nicht diesselbe wie id in payload
		if (id != song.getId()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Song id in payload must be equal to id in url.").build();
		}

		database.updateSong(song);
		return Response.status(Response.Status.NO_CONTENT).build();
	}


}
