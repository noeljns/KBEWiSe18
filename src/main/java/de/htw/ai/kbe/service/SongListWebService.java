package de.htw.ai.kbe.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.SongList;
import de.htw.ai.kbe.storage.SongListsDAO;
import de.htw.ai.kbe.storage.SongsDAO;

/**
 * Klasse um Songlisten zu verwalten
 * @author camilo, jns
 *
 */
//URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/songs
@Path("/songLists")
//durch @Secure werden alle Methoden gefiltert von RequestFilter
//@Secure
public class SongListWebService {
	
	
	@Context
	private HttpServletResponse response;
	
	private SongListsDAO database;

	@Inject
	public SongListWebService(SongListsDAO dao) {
		this.database = dao;
	}

	
	/**
	 * Methode um einen Song herauszugeben
	 * @return angefragten Song
	 */
	// TODO einheitlich Reponse schicken?
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<SongList> getAllSongLists(@QueryParam("userId") String userId) {
		List<SongList> songList = database.getAllSongLists();
		return songList;
	}
	
	/**
	 * Methode um eine Songliste herauszugeben
	 * @param id
	 * @return angefragte Songliste
	 */
	// TODO einheitlich Reponse schicken?
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public SongList getSongList(@PathParam("id") Integer id) {
		
		if (!database.isIdInDatabase(id)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

			try {
				response.flushBuffer();
				return null;
			} catch (Exception e) {
			}
		}

		SongList songlist = database.getSongListById(id);
		return songlist;
	}
	
	
	
}











