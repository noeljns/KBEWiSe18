package de.htw.ai.kbe.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.util.Set;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.SongList;
import de.htw.ai.kbe.storage.SongListsDAO;
import de.htw.ai.kbe.storage.SongsDAO;

/**
 * Klasse eines Webservices, der get, post und delete Anfragen von Songlisten
 * Objekten bearbeitet
 * 
 * @author camilo, jns
 *
 */
//URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/songLists
@Path("/songLists")
//durch @Secure werden alle Methoden gefiltert von RequestFilter
@Secure
public class SongListWebService {

	@Context
	private HttpServletResponse response;

	private SongListsDAO database;
	private SongsDAO songDatabase;

	@Inject
	public SongListWebService(SongListsDAO dao, SongsDAO sdao) {
		this.database = dao;
		this.songDatabase = sdao;
	}

	/**
	 * Methode um eine Songliste eines bestimmten Users herauszugeben
	 * 
	 * @param securityContext beinhaltet sicherheitsrelevante Informationen
	 * @param userId          Id des Users dem die Songliste gehört
	 * @return angefragten Song
	 */
	// TODO einheitlich Reponse schicken?
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<SongList> getAllSongLists(@Context SecurityContext securityContext,
			@QueryParam("userId") String userId) {
		// in Principal ist der durch RequestFilter authorifizierte User hinterlegt
		if (securityContext.getUserPrincipal().getName().equals(userId)) {
			// anfragender / authorifizierter User will seine eigene Listen
			// Beispiel: mmuster fragt Listen für mmuster an
			// daher: komplette Liste herausgeben, sprich public und private
			return database.getAllSongLists(userId);
		} else {
			// anfragender / authorifizierter User will die Listen eines anderen Users
			// Beispiel: mmuster fragt Listen für eschuler an
			// daher: nur public Listen herausgeben
			return database.getAllPublicSongLists(userId);
		}
	}

	/**
	 * Methode um eine Songliste mit einer bestimmten Id herauszugeben
	 * 
	 * @param securityContext beinhaltet sicherheitsrelevante Informationen
	 * @param id
	 * @return angefragte Songliste, falls sie public ist oder anfragendem User
	 *         gehört
	 */
	// TODO einheitlich Reponse schicken?
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public SongList getSongList(@Context SecurityContext securityContext, @PathParam("id") Integer id) {

		// es existiert keine Songliste mit der angefragten id in der Datenbank
		if (!database.isIdInDatabase(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

			try {
				response.flushBuffer();
				return null;
			} catch (Exception e) {
			}
		}

		SongList songlist = database.getSongListById(id);

		// anfragender / authorifizierter User will seine eigene Liste oder eine fremde
		// Liste
		// in Principal ist der durch RequestFilter authorifizierte User hinterlegt
		// Beispiel: mmuster will songlist 3 und diese gehört auch mmuster
		if (securityContext.getUserPrincipal().getName().equals(songlist.getOwner().getUsername())) {
			return songlist;
		}

		// anfragender / authorifizierter User will Liste eines anderen Users und die
		// Liste ist public
		// Beispiel: mmuster will songlist 3 und diese gehört aber eschuler, ist aber
		// public
		else if (!songlist.isPrivate()) {
			return songlist;
		}

		// anfragender / authorifizierter User will Liste eines anderen Users und die
		// Liste ist private
		// Beispiel: mmuster will songlist 3 und diese gehört aber eschuler, ist aber
		// private
		else if (songlist.isPrivate()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

		// etwas ist schief gelaufen
		return null;
	}

	/**
	 * Methode zur Bearbeitung einer Request zum Hinzufügen einer Songliste
	 * 
	 * @param songList
	 * @return response
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createSongList(@Context UriInfo uriInfo, SongList songList) {
		// wird immer für mmuster angelegt
		// URL mit neuer ID für die neue Songliste wird im Location Header
		// zurückgeschickt
		
		if(songList== null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("there is no payload").build();
		}
		
		if(songList.getSongList() == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("there are no songs in this list").build();
		}
		
		// zuerst prüfen, ob die songs im payload tatsächlich existieren
		Set<Song> songs = songList.getSongList();
		// List<Integer> songIds = new ArrayList<Integer>();
		
		for (Song song : songs) {
			if (!songDatabase.isIdInDatabase(song.getId())) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}	
		}
		
		// TEST
		System.out.println("id: " + songList.getId());
		System.out.println("owner: " + songList.getOwner());
		System.out.println("private: " + songList.isPrivate());
		System.out.println("songs: " + songList.getSongList());
		System.out.println("toString: " + songList.toString());

		// datenbank aufrufen um songlist zu adden
		Integer newId= database.addSongList(songList);
		System.out.println("new id: " + newId.intValue());
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		uriBuilder.path(Integer.toString(newId));
		return Response.created(uriBuilder.build()).build();
	}

	/**
	 * Methode um eine Songliste zu löschen
	 * 
	 * @param securityContext beinhaltet sicherheitsrelevante Informationen
	 * @param id
	 * @return response
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteSongList(@Context SecurityContext securityContext, @PathParam("id") Integer id) {

		// es existiert keine Songliste mit der angefragten id in der Datenbank
		if (!database.isIdInDatabase(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

			try {
				response.flushBuffer();
				return null;
			} catch (Exception e) {
			}
		}

		SongList songlist = database.getSongListById(id);

		// anfragender / authorifizierter User will seine eigene Liste löschen
		// in Principal ist der durch RequestFilter authorifizierte User hinterlegt
		// Beispiel: mmuster will songlist 3 löschen und diese gehört auch mmuster
		if (securityContext.getUserPrincipal().getName().equals(songlist.getOwner().getUsername())) {
			/// hier DAO methode aufrufen zum löschen des songLists
			database.deleteSongList(id);
			return Response.status(Response.Status.OK).build();
		}
		// Beispiel: mmuster will songlist 3 löschen aber diese gehört eschuler
		return Response.status(Response.Status.FORBIDDEN).build();
	}

}
