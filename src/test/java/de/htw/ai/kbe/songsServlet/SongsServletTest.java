package de.htw.ai.kbe.songsServlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

public class SongsServletTest {

	private SongsServlet servlet;
	private MockServletConfig config;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private final static String URITODB_STRING = "/Users/camiloocampo/Desktop/database/songs.json";
	//private final static String URITODB_STRING = "/Users/jns/KBE/database/songs.json";

	@Before
	public void setUp() throws ServletException {
		servlet = new SongsServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		config = new MockServletConfig();
		config.addInitParameter("uriToDatabaseFile", URITODB_STRING);
		servlet.init(config); // throws ServletException
	}

	@Test
	public void initShouldSetDBFileName() {
		assertEquals(URITODB_STRING, servlet.getDatabaseFileName());
	}
	
//	@Test
//	public void doGetShouldReturnTwoHundredWithWildCardHeader(){
//		
//		request.addHeader("*", "*");
//		try {
//			servlet.doGet(request, response);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		assertEquals(response.getStatus(), 200);
//		
//	}
	
	

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
