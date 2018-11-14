
package de.htw.ai.kbe.songsServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SongsServlet extends HttpServlet {

	private String uriToDB = null;
	private List<OurSong> readSongs= null;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		// Beispiel: Laden eines Konfigurationsparameters aus der web.xml
		this.uriToDB = servletConfig.getInitParameter("uriToDBComponent");
	
//		try {
//		    readSongs = readJSONToSongs("/Users/camiloocampo/Desktop/database/songs.json");
//			readSongs.forEach(s -> {
//				System.out.println(s.getArtist());
//			});
//			writeSongsToJSON(readSongs, "outJacksonSongs.json");
//		} catch (Exception e) { // Was stimmt hier nicht?
//		}
	}

	// Reads a list of songs from a JSON-file into List<Song>
	@SuppressWarnings("unchecked")
	static List<OurSong> readJSONToSongs(String filename) throws FileNotFoundException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
			return (List<OurSong>) objectMapper.readValue(is, new TypeReference<List<OurSong>>() {
			});
		}
	}

	// Write a List<Song> to a JSON-file
	static void writeSongsToJSON(List<OurSong> songs, String filename) throws FileNotFoundException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try (OutputStream os = new BufferedOutputStream(new FileOutputStream(filename))) {
			objectMapper.writeValue(os, songs);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// alle Parameter (keys)
		Enumeration<String> paramNames = request.getParameterNames();

		String responseStr = "";
		String param = "";
		while (paramNames.hasMoreElements()) {
			param = paramNames.nextElement();
			responseStr = responseStr + param + "=" 
			+ request.getParameter(param) + "\n";
		}
		response.setContentType("text/plain");
		try (PrintWriter out = response.getWriter()) {
			responseStr += "\n Your request will be sent to " + uriToDB;
			out.println(responseStr);
		}
		
		
		//try (PrintWriter out = response.getWriter()) {
		//	responseStr += "\n erster song. " + readSongs.get(0).toString();
		//	out.println(responseStr);
		//}
		
		
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		ServletInputStream inputStream = request.getInputStream();
		byte[] inBytes = IOUtils.toByteArray(inputStream);
		try (PrintWriter out = response.getWriter()) {
			out.println(new String(inBytes));
		}
	}

	protected String getUriToDB() {
		return this.uriToDB;
	}

}
