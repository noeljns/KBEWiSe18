package de.htw.ai.kbe.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import de.htw.ai.kbe.storage.IAuthDatabase;

// NEU ist @Provider
@Provider
@Secure
@Priority(Priorities.AUTHORIZATION)
public class RequestFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resinfo;
	private IAuthDatabase authdb;
	
	@Inject
	public RequestFilter(IAuthDatabase authDB) {
		System.out.println("RequestFilter was created and authDB. \n");
		this.authdb = authDB;
	}

	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		// AuthService wird nicht gefiltert, sprich folgender Aufruf geht immer durch: 
		// GET /songsRX/rest/auth?userID=mmuster

		// Get http:://localhost:8080/songsRX/rest/songs
		//System.out.println("A request like Get http:://localhost:8080/songsRX/rest/songs was sent.");
				
		List<String> token = context.getHeaders().getOrDefault("Authorization", null);
		// es wurde kein token mitgeschickt
		if (token == null) {
			System.out.println("Request does not contain any token");
			System.out.println();
			context.abortWith(Response.serverError().status(Status.UNAUTHORIZED).build());
		}
		// es wurde ein token mitgeschickt, aber er ist nicht valide
		else if (authdb.isTokenValid(token.get(0)) == false) {
			System.out.println("Request contains a token that is not valid: " + token.get(0));
			System.out.println();
			context.abortWith(Response.serverError().status(Status.FORBIDDEN).build());
		}
		// es wurde ein valider token mitgeschickt
		else {
			System.out.println("Auth was successful");
		}
	}
}
