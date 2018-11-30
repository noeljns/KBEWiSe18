package de.htw.ai.kbe.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.htw.ai.kbe.bean.Song;
import de.htw.ai.kbe.storage.IDatabaseSongs;
import de.htw.ai.kbe.storage.InMemoryDatabaseSongs;

/**
 * Klasse eines Webservices, der get und post Anfragen von Song Objekten
 * bearbeitet
 * 
 * @author jns, camilo
 *
 */

//URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/songs
@Path("/songs")
public class SongsServlet{
	
	private IDatabaseSongs database = new InMemoryDatabaseSongs();

	@Inject
	public SongsServlet(IDatabaseSongs database, UriInfo uriInfo) {
		super();
		this.database = database;
		this.uriInfo = uriInfo;
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
	 * Methode, um Servlet zu initiieren und Datenbank zu laden	
	 */
	//@Override
//	public void init(ServletConfig servletConfig) throws ServletException {
//		this.databaseFileName = servletConfig.getInitParameter("uriToDatabaseFile");
//		this.database = new DatabaseSongs(databaseFileName);
//		this.database.load();
//	}

	/**
	 * Methode, um Servlet zu entsorgen und Datenbank final zu speichern
	 */
//	@Override
//	public void destroy() {
//		if (database != null) {
//			database.save();
//		}
//	}

	/**
	 * Methode zur Bearbeitung einer http Request eines Clients zur Abfrage eines
	 * oder aller Songs
	 * 
	 * @param request  Http Request an unser Servlet
	 * @param response Http Request response
	 */
//	@GET
//	@Produces({ "application/json", "application/xml" })
//	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		// check database
//		if (database == null) {
//			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return;
//		}
//
//		// check header
//		String acceptValues = request.getHeader("Accept");
//		if (acceptValues != null && !(acceptValues.contains("*") || acceptValues.contains("*/*")
//				|| acceptValues.contains(APPLICATION) || acceptValues.contains(APPLICATION_JSON))) {
//
//			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
//			return;
//		}
//
//		Map<String, String[]> parameters = request.getParameterMap();
//
//		// check parameter
//		if (!areParametersValid(parameters)) {
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
//
//		if (parameters.containsKey(PARAMETER_ALL)) {
//			String json = getAllSongsAsJson();
//			sendContent(response, json);
//		} else if (parameters.containsKey(PARAMETER_SONG)) {
//			try {
//				int id = Integer.parseInt(parameters.get(PARAMETER_SONG)[0]);
//				String json = getSingleSongAsJson(id);
//				sendContent(response, json);
//			} catch (NumberFormatException e) {
//				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//				return;
//			}
//		}
//	}

	/**
	 * Methode zur Bearbeitung einer http Request eines Clients zum Hinzufügen eines
	 * Songs
	 * 
	 * @param request  Http Request an unser Servlet
	 * @param response Http Request response
	 */
	
	@Context
	UriInfo uriInfo;
	
//	@Override
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		if (!APPLICATION_JSON.equals(request.getContentType())) {
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
//		String encoding = request.getCharacterEncoding();
//		if (encoding == null) {
//			encoding = "UTF-8";
//		}
//
//		byte[] input = IOUtils.toByteArray(request.getInputStream());
//		String content = new String(input, encoding);
//		Song song = parseSongFromJsonToSong(content);
//
//		if (song == null) {
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
//
//		// Song muss zwingend einen Titel enthalten
//		if (song.getTitle() == null) {
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
//
//		database.addSong(song);
//		StringBuffer createdSongLocation = request.getRequestURL().append("?").append(PARAMETER_SONG).append("=")
//				.append(song.getId());
//
//		response.setStatus(HttpServletResponse.SC_CREATED);
//		response.addHeader("Location", createdSongLocation.toString());
//	}
//
//	String getDatabaseFileName() {
//		return this.databaseFileName;
//	}
//
//	DatabaseSongs getDatabase() {
//		return database;
//	}

	/**
	 * Methode prüft ob die Anfrage den validen Parameternamen all oder songIg
	 * enthält
	 * 
	 * @return true falls die Parameter valide sind mit den Werten all oder songId,
	 *         ansonsten false
	 */
//	private boolean areParametersValid(Map<String, String[]> parameters) {
//		return parameters.containsKey("songId") || parameters.containsKey("all");
//	}

	/**
	 * Methode zum Erstellen einer Antwort an den Client
	 * 
	 * @param response Http Response
	 * @param content  enthält entweder einen oder alle Songs der Datenbank
	 * @throws IOException
	 */
//	private void sendContent(HttpServletResponse response, String content) throws IOException {
//		if (content == null) {
//			response.sendError(HttpServletResponse.SC_NOT_FOUND);
//			return;
//		}
//
//		response.setContentType(APPLICATION_JSON);
//		response.setCharacterEncoding("UTF-8");
//		response.setStatus(HttpServletResponse.SC_OK);
//
//		try (PrintWriter out = response.getWriter()) {
//			out.println(content);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Methode holt alle Songs in der Datenbank in json
	 * 
	 * @return json String aller Songs in der Datenbank
	 */
//	private String getAllSongsAsJson() {
//		List<Song> songsAsList = database.getAllSongs();
//		Song[] songsAsArray = songsAsList.toArray(new Song[songsAsList.size()]);
//
//		try {
//			return mapper.writeValueAsString(songsAsArray);
//		} catch (JsonProcessingException e) {
//			return null;
//		}
//	}

	/**
	 * Methode um Song mit bestimmter id aus Datenbank in json zu holen
	 * 
	 * @param id des angeforderten Songs
	 * @return json String des angeforderten Songs
	 */
//	private String getSingleSongAsJson(int id) {
//		Song song = database.getSongById(id);
//		if (song == null) {
//			return null;
//		}
//
//		try {
//			return mapper.writeValueAsString(song);
//		} catch (JsonProcessingException e) {
//			return null;
//		}
//	}

	/**
	 * Methode um übergebenen Json Song in ein Song Objekt zu parsen
	 * 
	 * @param content json String eines Songs
	 * @return Songobjekt
	 */
//	private Song parseSongFromJsonToSong(String content) {
//		try {
//			return mapper.readValue(content, Song.class);
//		} catch (JsonParseException e) {
//			return null;
//		} catch (JsonMappingException e) {
//			return null;
//		} catch (IOException e) {
//			return null;
//		}
//	}
}
