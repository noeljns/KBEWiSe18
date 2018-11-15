
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
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Klasse eines Webservices, der get und post Anfragen von Song Objekten bearbeitet
 * @author jns, camilo
 *
 */
public class SongsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String databaseFileName;
	private DatabaseSongs database; 
	// private List<Song> songs;
	

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		this.databaseFileName = servletConfig.getInitParameter("uriToDatabaseFile");
		this.database = new DatabaseSongs(databaseFileName);
	}

	@Override
	public void destroy() {
		System.out.print("Methode destroy() wurde aufgerufen, Servlet wird vom Container entsorgt.");
		
		if(database != null) {
			this.database.save();		
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (database == null) {
			response.sendError(500);
			// response.setStatus(500);
			return;
		}
		// Test, wird in $TOM_HOME/logs/catalina.out geprintet
		System.out.println("GET request: " + request.getPathInfo() + " / " + request.getQueryString());
		
		// Test: drucke die IDs, Artists und Title aller songs in log file
		database.getSongs().forEach(
				s -> {System.out.println(s.getId() + ". Aartist: " + s.getArtist() + ", Title: " + s.getTitle() + "\n");});
		
		
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
		response.setContentType("text/plain");
		ServletInputStream inputStream = request.getInputStream();
		byte[] inBytes = IOUtils.toByteArray(inputStream);
		try (PrintWriter out = response.getWriter()) {
			out.println(new String(inBytes));
		}
	}

}
