package de.htw.ai.kbe.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
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
import de.htw.ai.kbe.storage.IDatabaseSongs;

/**
 * Klasse eines Webservices, der get und post Anfragen von Song Objekten
 * bearbeitet
 * 
 * @author jns, camilo
 *
 */
//URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/songs
@Path("/songs")
public class SongWebService {

	private IDatabaseSongs database;

	@Inject
	public SongWebService(IDatabaseSongs database) {
		super(); // nicht notwendig; würde automatisch im Hintergrund aufgerufen werden
		this.database = database;
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Song getSong(@PathParam("id") Integer id) {
		System.out.println("called the get song method in songsServlet for id " + id);
		Song song = database.getSongById(id);
		return song;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Song> getAllSongs() {
		System.out.println("called the get all songs method in songsServlet");
		List<Song> songList = database.getAllSongs();
		return songList;
	}

	/**
	 * Methode zur Bearbeitung einer http Request eines Clients zum Hinzufügen eines
	 * Songs
	 * 
	 * @param request  Http Request an unser Servlet
	 * @param response Http Request response
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
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		int newId = database.addSong(song);
		System.out.println("new id: " + newId);
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		uriBuilder.path(Integer.toString(newId));
		return Response.created(uriBuilder.build()).build();
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	public Response updateSong(@PathParam("id") Integer id, Song song) {
		if (song == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();

		}
		// Song muss zwingend einen Titel enthalten!
		if (song.getTitle() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		// wenn die id in unserer datenbank nicht vorhanden ist
		if(!database.isIdInDatabase(id)) {
			return Response.status(Response.Status.BAD_REQUEST).build();	
		}
		// id in url ist nicht diesselbe wie id in payload
		if(id != song.getId()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		database.updateSong(song);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Integer id) {
		// wenn die id in unserer datenbank nicht vorhanden ist
		if(!database.isIdInDatabase(id)) {
			return Response.status(Response.Status.BAD_REQUEST).build();	
		}
		database.deleteSong(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}