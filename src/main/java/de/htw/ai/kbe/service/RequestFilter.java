package de.htw.ai.kbe.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import de.htw.ai.kbe.bean.SongRXUser;
import de.htw.ai.kbe.storage.AuthDAO;

/**
 * Klasse, um Anfragen zu filtern
 * @author camilo, jns
 *
 */
@Provider
@Secure
public class RequestFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resinfo;
	private AuthDAO authdb;
	
	@Inject
	public RequestFilter(AuthDAO authDB) {
		this.authdb = authDB;
	}

	/**
	 * Methode filtert Anfragen ohne token oder ohne validen token heraus
	 */
	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		// AuthService wird nicht gefiltert, sprich folgender Aufruf geht immer durch: 
		// GET /songsRX/rest/auth?userID=mmuster
		// da AuthService Klasse nicht mit @Secure annotiert ist
		
		List<String> token = context.getHeaders().getOrDefault("Authorization", null);
		// es wurde kein token mitgeschickt
		if (token == null) {
			System.out.println("Request does not contain any token");
			System.out.println();
			context.abortWith(Response.serverError().status(Status.UNAUTHORIZED).build());
			return;
		}
		
		SongRXUser user = authdb.getUserByToken(token.get(0));
		// es wurde ein token mitgeschickt, aber er ist nicht valide
		// fuer diesen token wird kein user gefunden, token ist falsch
		if (user == null) {
			System.out.println("Request contains a token that is not valid: " + token.get(0));
			System.out.println();
			context.abortWith(Response.serverError().status(Status.UNAUTHORIZED).build());
		}
		// es wurde ein valider token mitgeschickt und der dazugehörige user damit identifiziert
		// Anfrage wird an Endpoints weitergeleitet
		else {
			System.out.println("Authentification / Authorization was successful");
			// legen username des authorifizierten Users in Principal Objekt des RequestSecurityContexts ab
			// sprich je nachdem eschuler oder mmuster
			// source: https://simplapi.wordpress.com/2015/09/19/jersey-jax-rs-securitycontext-in-action/
			context.setSecurityContext(new RequestSecurityContext(user.getUsername()));
		}
	}
}




















