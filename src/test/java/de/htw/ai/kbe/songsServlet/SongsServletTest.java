package de.htw.ai.kbe.songsServlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

public class SongsServletTest {

	private SongsServlet servlet;
	private DatabaseSongs database;
	private MockServletConfig config;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private final static String URITODB_STRING = "/Users/camiloocampo/Desktop/database/songs.json";
	// private final static String URITODB_STRING =
	// "/Users/jns/KBE/database/songs.json";

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
		List<Song> loadedSongs = database.getSongs();
		assertTrue(loadedSongs.size() == 10);

	}

	@Test
	public void initShouldLoadOurSongs() {
		List<Song> loadedSongs = database.getSongs();
		assertEquals(loadedSongs.get(0).getTitle(), "7 Years");
		assertEquals(loadedSongs.get(1).getTitle(), "Private Show");
		assertEquals(loadedSongs.get(2).getTitle(), "No");
		assertEquals(loadedSongs.get(3).getTitle(), "i hate u, i love u");
		assertEquals(loadedSongs.get(4).getTitle(), "I Took a Pill in Ibiza");
		assertEquals(loadedSongs.get(5).getTitle(), "Bad Things");
		assertEquals(loadedSongs.get(6).getTitle(), "Ghostbusters (I'm not a fraid)");
		assertEquals(loadedSongs.get(7).getTitle(), "Team");
		assertEquals(loadedSongs.get(8).getTitle(), "Mom");
		assertEquals(loadedSongs.get(9).getTitle(), "Can’t Stop the Feeling");
	}

	@Test
	public void doGetShouldReturnTwoHundredWithWildCardAcceptHeader() {
		
		
		request.addHeader("Accept", "*/*");
		try {
			servlet.doGet(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), 200);

	}

	
	@Test
	public void doGetShouldReturnTwoHundredWithApplicationJSONAcceptHeader() {
		
		
		request.addHeader("Accept", "application/json");
		try {
			servlet.doGet(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), 200);

	}
	
	@Test
	public void doGetShouldReturnTwoHundredWithNoAcceptHeader() {	
		try {
			servlet.doGet(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void doGetShouldReturnFourHundredWithXmlAcceptHeader() {
		request.addHeader("Accept", "application/xml");
		try {
			servlet.doGet(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), 406);

	}
	
	
	@Test
	public void doGetShouldReturnFourHundredWithHtmlAcceptHeader() {
		request.addHeader("Accept", "text/html");
		try {
			servlet.doGet(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), 406);

	}
	
	
	@Test
	public void doGetShouldAcceptAllParam() {
		
		
		request.addParameter("all", "");
		try {
			servlet.doGet(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
//	@Test
//	public void doGetShouldEchoParameters() throws ServletException, IOException {
//		request.addParameter("username", "scott");
//		request.addParameter("password", "tiger");
//
//		servlet.doGet(request, response);
//
//		assertEquals("text/plain", response.getContentType());
//		assertTrue(response.getContentAsString().contains("username=scott"));
//		assertTrue(response.getContentAsString().contains("password=tiger"));
//		assertTrue(response.getContentAsString().contains(URITODB_STRING));
//	}
//
//	@Test
//	public void doPostShouldEchoBody() throws ServletException, IOException {
//		request.setContent("blablablabla".getBytes());
//		servlet.doPost(request, response);
//		assertEquals("text/plain", response.getContentType());
//		assertTrue(response.getContentAsString().contains("blablablabla"));
//	}
}
