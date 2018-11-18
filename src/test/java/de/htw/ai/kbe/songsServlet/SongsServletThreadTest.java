package de.htw.ai.kbe.songsServlet;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;

@RunWith(ConcurrentTestRunner.class)
public class SongsServletThreadTest {

	public class SongsServletTest {

		private SongsServlet servlet;
		private DatabaseSongs database;
		private MockServletConfig config;
		private MockHttpServletRequest request;
		private MockHttpServletResponse response;

		// holen für die Tests song_test.json, weil wir die Datei im Laufzeittest
		// verändern
		// private final static String URITODB_STRING =
		// "/Users/jns/KBE/database/songs_test.json";
		private final static String URITODB_STRING_THREAD = "/Users/camiloocampo/Desktop/database/songs_test_thread.json";

		@Before
		public void setUp() throws ServletException {
			System.out.println("hello im the threading in setup");
			servlet = new SongsServlet();
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			config = new MockServletConfig();
			config.addInitParameter("uriToDatabaseFile", URITODB_STRING_THREAD);
			servlet.init(config); // throws ServletException
			database = servlet.getDatabase();

		}

		@Test
		public void doPost() throws Exception {
			System.out.println("hello im the threading in postOne method");
			JSONObject obj = new JSONObject();
			obj.put("title", "threadsafe");
			obj.put("artist", "Camilo");
			obj.put("album", "Jonas Jonas");
			obj.put("released", "2018");
			request.setContentType("application/json");
			request.setContent(obj.toString().getBytes("utf-8"));
			servlet.doPost(request, response);
			
		}

		@After
		public void testSizeOfDatabase() {
			System.out.println("hello im the threading in testsize");
			assertEquals(14, database.getAllSongs().size());

		}

	}
}
