package de.htw.ai.kbe.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.bean.SongList;
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
	
	private SongsDAO database;

	@Inject
	public SongListWebService(SongsDAO dao) {
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
		// existiert token?
		
		// ist es der korrekte token? sonst nur public
		return null;
	}
}











