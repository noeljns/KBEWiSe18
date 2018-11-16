package de.htw.ai.kbe.songsServlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Klasse eines Webservices, der get und post Anfragen von Song Objekten
 * bearbeitet
 * 
 * @author jns, camilo
 *
 */
public class SongsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String databaseFileName;
	private DatabaseSongs database;

	/**
	 * Methode, um Servlet zu initiieren und Datenbank zu laden
	 */
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		this.databaseFileName = servletConfig.getInitParameter("uriToDatabaseFile");
		this.database = new DatabaseSongs(databaseFileName);
	}

	/**
	 * Methode, um Servlet zu entsorgen und Datenbank final zu speichern
	 */
	@Override
	public void destroy() {
		System.out.print("Methode destroy() wurde aufgerufen, Servlet wird vom Container entsorgt.");

		if (getDatabase() != null) {
			this.getDatabase().save();
		}
	}

	/**
	 * Methode zur Bearbeitung einer http Request eines Clients
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// CHECK DATABASE
		if (getDatabase() == null) {
			System.out.println("Interne Datenbank ist leer.");
			response.sendError(500);
			// response.setStatus(500);
			return;
		}

		// GET AND CHECK ACCEPT HEADER
		// get accept header
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("vor GET print und direkt über acceptValues des getRequests.");
		String acceptValues = request.getHeader("accept");
		System.out.println("acceptValues des getRequests wurden geholt, siehe: " + acceptValues);

		// request hat accept header
		// aber eigentlich sollte es prüfen, ob accept header überhaupt nicht gesendet wird
		if (acceptValues != null) {
			// dieser accept header hat anderen accept type als gewünscht
			if (!(acceptValues.contains("*/*") || acceptValues.contains("application/json"))) {
				System.out.println(
						"Servlet kann doGet Anfrage nicht bearbeiten, da inkorrekter Accept Header des Clients.");
				response.sendError(406);
				// response.setStatus(406);
				return;
			}
		}

		// acceptValues ist nun entweder null, */* oder application/json
		System.out.println("doGet Anfrage hat Filterung des Accept Headers bestanden");

		// GET AND CHECK ACCEPT parameter
		// get parameter
		
		
		HashMap<String, String> parameters = getParameters(request);
		System.out.println("Folgende Paramternamen wurden übergeben: " + parameters.keySet().toString());
		System.out.println("Folgende Parameterwerte wurden übergeben: " + parameters.values().toString());

		// check parameter
		if (!areParametersValid(parameters)) {
			System.out.println("Parameter der Anfrage sind nicht valide. Erlaubt sind: all oder songId.");
			response.sendError(404);
			// response.setStatus(404);
			return;
		}

		// parameter sind nun entweder all oder songsId
		System.out.println("Parameter sind valide.");

		// get all songs as json
		boolean test1 = parameters.containsKey("all");
		System.out.println("Enthalten Parameter all?: " + test1);
		
		if (parameters.containsKey("all")) {
			System.out.println("in if Abfrage für Parameter all");
			respondAllSongs(response);
		}

		// get one specified song as json
		boolean test2 = parameters.containsKey("songsId");
		System.out.println("Enthalten Parameter all?: " + test2);
		
		if (parameters.containsKey("songId")) {
			int id = Integer.parseInt(parameters.get("songId"));
			System.out.println("in if Abfrage für Parameter songId");
			respondOneSong(response, id);
		}
		
		
		
		// Test, wird in $TOM_HOME/logs/catalina.out geprintet
		// System.out.println("GET request: " + request.getPathInfo() + " / " +
		// request.getQueryString());

		// Test get request headers:
		// System.out.println("GET request headers: " + request.getHeaderNames());

		// Test: drucke die IDs, Artists und Title aller songs in log file
//		getDatabase().getSongs().forEach(s -> {
//			System.out.println(s.getId() + ". Artist: " + s.getArtist() + ", Title: " + s.getTitle() + "\n");
//		});

		// Test: drucke alle Parameter (keys) der Request in log file
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			System.out.println("Parameter: " + paramNames.nextElement());
		}

		// doGet verlief erfolgreich
		response.setStatus(200);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {		
		System.out.println("in doPos Methode");
		String responseStr = null;
		int responseCode = -1;
		String responseType = null;
		
		String contentType = request.getContentType();
		ServletInputStream inputstream = request.getInputStream();
		String content = new String(IOUtils.toByteArray(inputstream));
		Song song = null;
		
		if( contentType != "application/json") {
			responseStr = "Der übergebene Song ist nicht in validem json Format und kann nicht eingelesen werden.";
			responseCode = HttpServletResponse.SC_BAD_REQUEST;
		}
		
		song = parseSongFromJsonToSong(content);
		System.out.println("Song wurde von json zu Objekt geparst");
		System.out.println("Titel des Songs: " + song.getTitle());
		System.out.println("ID des Songs: " + song.getId());


		// checken, ob das parsen zu Song Objekt erfolgreich war
		if (song != null) { 
            database.addSong(song);
    			System.out.println("Der Song wurde erfolgreich eingespeist. Die ID des Songs lautet: "+ song.getId());
            responseStr = "Der Song wurde erfolgreich eingespeist. Die ID des Songs lautet: "+ song.getId();
            responseCode = HttpServletResponse.SC_CREATED;
            responseType = "application/json";
        }
		
        response.setStatus(responseCode);
        response.setContentType(responseType);
        // neue ID soll an Client als Wert des Location Heders der Response zurückgeschickt werden
        // http://localhost:8080/songsServlet?songIde=newIde
        // TODO
        response.addHeader("Location", song.getId().toString());
        
        try (PrintWriter out = response.getWriter()) {
            out.println(responseStr);
        }
	}
	
	
	private Song parseSongFromJsonToSong(String content) {
        try {
			return new ObjectMapper().readValue(content, Song.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	protected String getDatabaseFileName() {
		return this.databaseFileName;
	}

	public DatabaseSongs getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseSongs database) {
		this.database = database;
	}

	/**
	 * filtert Parametername und dessen Wert aus request heraus
	 * 
	 * @param request
	 * @return Paramtername und zugehöriger Wert
	 */
	private static HashMap<String, String> getParameters(HttpServletRequest request) {
		HashMap<String, String> parameters = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		String parametername = null;
		while (parameterNames.hasMoreElements()) {
			parametername = parameterNames.nextElement();
			parameters.put(parametername, request.getParameter(parametername));
		}
		return parameters;
	}
	
	/**
	 * Methode prüft ob die Anfrage den validen Parameternamen all oder songIg enthält
	 */
	boolean areParametersValid(HashMap<String, String> parameters) {
		System.out.println("in areParametersValid");
		return parameters.containsKey("songId") || parameters.containsKey("all");
	}
	
	/**
	 * Methode erstellt Response an Client mit allen Songs in der Datenbank in json
	 * @param response
	 */
	private void respondAllSongs(HttpServletResponse response) {
		String responseStr = getAllSongsAsJson();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		
		try (PrintWriter out = response.getWriter()) {
		    out.println(responseStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode holt alle Songs in der Datenbank in json
	 * @return
	 */
	private String getAllSongsAsJson() {
		List<Song> songsAsList = database.getSongs();
		Song[] songArr = new Song[songsAsList.size()];
		songArr = songsAsList.toArray(songArr);
		
		ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        try {
			return om.writeValueAsString(songArr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
		
	/**
	 * Methode erstellt Response an Client mit Song mit bestimmter ID in json
	 */
	private void respondOneSong(HttpServletResponse response, int id) {
		System.out.println("in respondOneSong");
		
		String responseStr = getOneSongAsJson(id);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		
		try (PrintWriter out = response.getWriter()) {
		    out.println(responseStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode um Song mit bestimmter id aus Datenbank in json zu holen
	 */
	private String getOneSongAsJson(int id) {
		System.out.println("in getOneSongAsJson");

		Song song = this.database.getSongById(id);
        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        
        try {
			return om.writeValueAsString(song);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	

	  



}
