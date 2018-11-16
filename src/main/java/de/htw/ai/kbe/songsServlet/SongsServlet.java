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
		
		if(getDatabase() != null) {
			this.getDatabase().save();		
		}
	}
	
	
	/**
	 * Methode zur Bearbeitung einer http Request eines Clients
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (getDatabase() == null) {
			response.sendError(500);
			// response.setStatus(500);
			return;
		}
		
		// check parameter
		// all erlaubt oder songsId=int

				
		// get accept header
		System.out.println("vor GET print und direkt über acceptValues des getRequests.");
		String acceptValues = request.getHeader("accept");
		System.out.println("acceptValues des getRequests wurden geholt, siehe: " + acceptValues + "\n");

		// check accept header: 
		
		// request hat accept header
		if( acceptValues != null) {
			// dieser accept header hat anderen accept type als gewünscht
			if ( !(acceptValues.contains("*/*") || acceptValues.contains("application/json")) ) {
				System.out.println("Servlet kann doGet Anfrage nicht bearbeiten, da inkorrekter Accept Header des Clients.");
				response.sendError(406);
				return;
			}
		}
		
		// acceptValues ist nun entweder null, */* oder application/json
		System.out.println("doGet Anfrage hat Filterung des Accept Headers bestanden");
		

		
		// Test, wird in $TOM_HOME/logs/catalina.out geprintet
		System.out.println("GET request: " + request.getPathInfo() + " / " + request.getQueryString());
	
		//Test get request headers:
		// System.out.println("GET request headers: " + request.getHeaderNames());
		
		// Test: drucke die IDs, Artists und Title aller songs in log file
		getDatabase().getSongs().forEach(
				s -> {System.out.println(s.getId() + ". Artist: " + s.getArtist() + ", Title: " + s.getTitle() + "\n");});
		

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
	
	protected String getDatabaseFileName () {
		return this.databaseFileName;
	}

	public DatabaseSongs getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseSongs database) {
		this.database = database;
	}

}
