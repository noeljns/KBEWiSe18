package de.htw.ai.kbe.songsServlet;

import javax.servlet.ServletConfig;

public class App {

	public static void main(String[] args) {
		// ServletConfig servletConfig = new ServletConfig();
		
		try {
			SongsServlet webserver = new SongsServlet();
			
			System.out.println("hello");
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
