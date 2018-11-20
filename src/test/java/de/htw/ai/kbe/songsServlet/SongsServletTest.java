package de.htw.ai.kbe.songsServlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

/**
 * Testklasse eines Webservices, der get und post Anfragen von Song Objekten
 * bearbeitet
 * 
 * @author jns, camilo
 *
 */
public class SongsServletTest {

	private SongsServlet servlet;
	private DatabaseSongs database;
	private MockServletConfig config;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	// holen für Tests song_test.json, weil wir Datei im Laufzeittest verändern
	private final static String URITODB_STRING = "/Users/jns/KBE/database/songs_test.json";
	private final static String URITODB_STRING_POST = "/Users/jns/KBE/database/songs_test_post.json";
	// private final static String URITODB_STRING =
	// "/Users/camiloocampo/Desktop/database/songs_test.json";
	// private final static String URITODB_STRING_POST =
	// "/Users/camiloocampo/Desktop/database/songs_test_post.json";
	// private final static String URITODB_STRING =
	// "/home/s0558239/database/songs_test.json";
	// private final static String URITODB_STRING_POST =
	// "/home/s0558239/database/songs_test_post.json";

	@Before
	public void setUp() throws ServletException {
		servlet = new SongsServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		config = new MockServletConfig();
		config.addInitParameter("uriToDatabaseFile", URITODB_STRING);
		servlet.init(config); // throws ServletException
		database = servlet.getDatabase();
	}

	@Test
	public void initShouldSetDBFileName() {
		assertEquals(URITODB_STRING, servlet.getDatabaseFileName());
	}

	@Test
	public void initShouldLoadTenSongs() {
		List<Song> loadedSongs = database.getAllSongs();
		assertTrue(loadedSongs.size() == 10);
	}

	@Test
	public void initShouldLoadOurSongs() {
		List<Song> loadedSongs = database.getAllSongs();
		assertEquals(loadedSongs.get(0).getTitle(), "Can’t Stop the Feeling");
		assertEquals(loadedSongs.get(1).getTitle(), "Mom");
		assertEquals(loadedSongs.get(2).getTitle(), "Team");
		assertEquals(loadedSongs.get(3).getTitle(), "Ghostbusters (I'm not a fraid)");
		assertEquals(loadedSongs.get(4).getTitle(), "Bad Things");
		assertEquals(loadedSongs.get(5).getTitle(), "I Took a Pill in Ibiza");
		assertEquals(loadedSongs.get(6).getTitle(), "i hate u, i love u");
		assertEquals(loadedSongs.get(7).getTitle(), "No");
		assertEquals(loadedSongs.get(8).getTitle(), "Private Show");
		assertEquals(loadedSongs.get(9).getTitle(), "7 Years");
	}

	@Test
	public void doGetShouldReturnTwoHundredWithWildCardAcceptHeader() throws Exception {
		request.addHeader("Accept", "*/*");
		request.addParameter("all", "");

		servlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void doGetShouldReturnTwoHundredWithApplicationJSONAcceptHeader() throws Exception {
		request.addHeader("Accept", "application/json");
		request.addParameter("all", "");

		servlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void doGetShouldReturnTwoHundredWithNoAcceptHeader() throws Exception {
		request.addParameter("all", "");

		servlet.doGet(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void doGetShouldReturnFourHundredWithXMLAcceptHeader() throws Exception {
		request.addHeader("Accept", "application/xml");
		request.addParameter("all", "");

		servlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_NOT_ACCEPTABLE, response.getStatus());
	}

	@Test
	public void doGetShouldReturnFourHundredWithHTMLAcceptHeader() throws Exception {
		request.addHeader("Accept", "text/html");
		request.addParameter("all", "");

		servlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_NOT_ACCEPTABLE, response.getStatus());
	}

	// if the accept header exists, but neither contains * nor application/json, then reject with http status code 406
	@Test
	public void doGetShouldOnlyAcceptWildCardOrJson() throws Exception {
		request.addHeader("Accept", "text/html");
		request.addParameter("all", "istEgal");

		servlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_NOT_ACCEPTABLE, response.getStatus());
	}

	@Test
	public void doGetShouldReturnTwoHundredWithAllParam() throws Exception {
		request.addParameter("all", "");

		servlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void doGetShouldReturnTwoHundredWithSongIdParam() throws Exception {
		request.addParameter("songId", "3");

		servlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void doGetReturnsAllSongsInJson() throws Exception {
		request.addHeader("Accept", "application/json");
		request.addParameter("all", "");

		servlet.doGet(request, response);

		// allSongs are in json format
		assertEquals(response.getContentType(), "application/json");

		// response contains all songs
		String content = response.getContentAsString();
		assertTrue(content.contains("Can’t Stop the Feeling"));
		assertTrue(content.contains("Mom"));
		assertTrue(content.contains("Team"));
		assertTrue(content.contains("Ghostbusters (I'm not a fraid)"));
		assertTrue(content.contains("Bad Things"));
		assertTrue(content.contains("I Took a Pill in Ibiza"));
		assertTrue(content.contains("i hate u, i love u"));
		assertTrue(content.contains("No"));
		assertTrue(content.contains("Private Show"));
		assertTrue(content.contains("7 Years"));
	}

	@Test
	public void doGetWithParamSongIdSixReturnsSongSixAsJson() throws Exception {
		request.addHeader("Accept", "application/json");
		request.addParameter("songId", "6");

		servlet.doGet(request, response);

		// songId=6 is in json format
		assertEquals(response.getContentType(), "application/json");
		
		// response contains song with songId=6
		String content = response.getContentAsString();
		assertTrue(content.contains("6"));
		assertTrue(content.contains("I Took a Pill in Ibiza"));
	}

	@Test
	public void doGetWithIncorrectLowerCaseSongIdParamReturnsBadRequest() throws Exception {
		request.addHeader("Accept", "application/json");
		// songid instead of songId
		request.addParameter("songid", "6");

		servlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
	}

	@Test
	public void doGetWithNotExistingSongIdReturnsBadRequest() throws Exception {
		request.addHeader("Accept", "application/json");
		// song with songId=600 does not exist in data base
		request.addParameter("songid", "600");

		servlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
	}

	@Test
	public void doPostWithCorrectJsonPayloadReturnsCorrectLocalHeader() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("title", "Testing with JUnit");
		obj.put("artist", "Camilo");
		obj.put("album", "Jonas Jonas");
		obj.put("released", "2018");
		
		request.setContentType("application/json");
		request.setContent(obj.toString().getBytes("utf-8"));
		servlet.doPost(request, response);
		Song postedSong = database.getSongById(11);
		
		assertEquals(postedSong.getTitle(), "Testing with JUnit");
		assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
		assertEquals("http://localhost?songId=11", response.getHeader("Location"));
	}

	@Test
	public void doPostWithoutTitleReturnsBadRequest() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("title", null);
		obj.put("artist", "Camilo");
		obj.put("album", "Jonas Jonas");
		obj.put("released", "2018");
		
		request.setContentType("application/json");
		request.setContent(obj.toString().getBytes("utf-8"));
		servlet.doPost(request, response);

		assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
	}

	@Test
	public void doPostWithoutJsonPayloadReturnsBadRequest() throws Exception {
		request.setContent("noJsonFormat".getBytes());
		servlet.doPost(request, response);
		
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
	}

	@Test
	public void shutDownSavesDatabaseState() throws Exception {
		config = new MockServletConfig();
		config.addInitParameter("uriToDatabaseFile", URITODB_STRING_POST);
		servlet.init(config); // throws ServletException
		database = servlet.getDatabase();
		int size = database.getAllSongs().size();

		JSONObject obj = new JSONObject();
		obj.put("title", "i love junit");
		obj.put("artist", "Camilo");
		obj.put("album", "Jonas Jonas");
		obj.put("released", "2018");
		
		request.setContentType("application/json");
		request.setContent(obj.toString().getBytes("utf-8"));
		servlet.doPost(request, response);
		servlet.destroy();
		
		assertEquals(size+1, database.getAllSongs().size());
	}
}
