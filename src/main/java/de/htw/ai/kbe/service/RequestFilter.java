package de.htw.ai.kbe.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import de.htw.ai.kbe.storage.AuthDatabase;

// NEU ist @Provider
@Provider
public class RequestFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resinfo;
	private AuthDatabase authdb;

	@Inject
	public RequestFilter(AuthDatabase authDB) {
		System.out.println("Filter was created \n");
		this.authdb = authDB;
	}

	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		// Get /songsRX/rest/auth?userID=mmuster
		// AuthService wird aufgerufen
		if (resinfo.getResourceClass().getName().equals(AuthService.class.getName())) {
			System.out.println("AuthService is called to check whether userId exists \n");
		}
		// Get http:://localhost:8080/songsRX/rest/songs
		else {
			System.out.println("A request like Get http:://localhost:8080/songsRX/rest/songs was sent. \n");
			ResponseBuilder responseBuilder = null;
			Response response = null;
			List<String> token = context.getHeaders().getOrDefault("Authorization", null);
			if (token == null) {
				System.out.println("Request does not contain any token \n");
				responseBuilder = Response.serverError();
				response = responseBuilder.status(Status.UNAUTHORIZED).build();
				context.abortWith(response);
			} else if (authdb.isTokenValid(token.get(0)) == false) {
				System.out.println("Request does contain a token, but it is not valid \n");
				responseBuilder = Response.serverError();
				response = responseBuilder.status(Status.FORBIDDEN).build();
				context.abortWith(response);
			} else {
				System.out.println("Auth was successful");
			}
		}
	}

}
