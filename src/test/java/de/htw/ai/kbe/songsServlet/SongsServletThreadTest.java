package de.htw.ai.kbe.songsServlet;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
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

	private SongsServlet servlet;
	private DatabaseSongs database;
	private MockServletConfig config;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	// private final static String URITODB_STRING_THREAD =
	// "/Users/camiloocampo/Desktop/database/songs_test_thread.json";
	// private final static String URITODB_STRING_THREAD =
	// "/home/s0558239/database/songs_test_thread.json";
	private final static String URITODB_STRING_THREAD = "/Users/jns/KBE/database/songs_test_thread.json";

	@Before
	public void setUp() throws ServletException {
		servlet = new SongsServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		config = new MockServletConfig();
		config.addInitParameter("uriToDatabaseFile", URITODB_STRING_THREAD);
		servlet.init(config); // throws ServletException
		database = servlet.getDatabase();
	}

	@Test
	public void checkConcurrentWrite() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("title", "checkConcurrentWrite");
		obj.put("artist", "Camilo");
		obj.put("album", "Jonas Jonas");
		obj.put("released", "2018");

		request.setContentType("application/json");
		request.setContent(obj.toString().getBytes("utf-8"));
		servlet.doPost(request, response);
	}

	@After
	public void testSizeOfDatabase() {
		assertEquals(15, database.getAllSongs().size());
	}

	@Test
	public void checkWriteAndRead() throws Exception {
		// thread 1 sends a do post request
		if(Thread.currentThread().getName().contains("Thread-1")) {
			System.out.println("start of Thread-1");
			JSONObject obj = new JSONObject();
			obj.put("title", "checkWriteAndRead");
			obj.put("artist", "Camilo");
			obj.put("album", "Jonas Jonas");
			obj.put("released", "2018");
			request.setContentType("application/json");
			request.setContent(obj.toString().getBytes("utf-8"));
			servlet.doPost(request, response);
			Thread.sleep(5000);
			database.save();
			System.out.println("amount of songs in servlet datbase while in Thread-1: " + database.getAllSongs().size());
			System.out.println("end of Thread-1");
		}

		// meanwhile thread 2 tries to read
		if(Thread.currentThread().getName().contains("Thread-2")) {
			System.out.println("start of Thread-2");
			request.addHeader("Accept", "*/*");
			request.addParameter("all", "");
			servlet.doGet(request, response);
			System.out.println("response from 2nd thread " + response.getContentAsString());
			System.out
					.println("response from 2nd thread with respect to database " + database.getAllSongs().toString());
			System.out.println("amount of songs in servlet datbase while in Thread-2: " + database.getAllSongs().size());
			System.out.println("end of Thread-2");
		}
	}
	
	@Test
	@Ignore
	public void checkMultipleWriteAndRead() throws Exception {
		// thread 1 tries to read
		if (Thread.currentThread().getName().contains("Thread-1")) {
			System.out.println("start of Thread-1");
			request.addHeader("Accept", "*/*");
			request.addParameter("all", "");
			servlet.doGet(request, response);
			System.out.println("response from 2nd thread " + response.getContentAsString());
			System.out.println(
					"response from first thread with respect to database " + database.getAllSongs().toString());
			System.out.println("amount of songs in servlet datbase while in Thread-1: " + database.getAllSongs().size());
			System.out.println("end of Thread-1");
		}

		// thread 2 sends a do post request
		if (Thread.currentThread().getName().contains("Thread-2")) {
			System.out.println("start of Thread-2");
			JSONObject obj = new JSONObject();
			obj.put("title", "checkWriteAndRead");
			obj.put("artist", "Camilo");
			obj.put("album", "Jonas Jonas");
			obj.put("released", "2018");
			request.setContentType("application/json");
			request.setContent(obj.toString().getBytes("utf-8"));
			servlet.doPost(request, response);
			Thread.sleep(5000);
			// database.save();
			System.out.println("amount of songs in servlet datbase while in Thread-2: " + database.getAllSongs().size());
			System.out.println("end of Thread-2");
		}

		// meanwhile thread 3 tries to read
		if (Thread.currentThread().getName().contains("Thread-3")) {
			System.out.println("start of Thread-3");
			request.addHeader("Accept", "*/*");
			request.addParameter("all", "");
			servlet.doGet(request, response);
			System.out.println("response from 3nd thread " + response.getContentAsString());
		
			System.out.println("amount of songs in servlet datbase while in Thread-3: " + database.getAllSongs().size());
			System.out.println("end of Thread-3");
		}
	}

}
