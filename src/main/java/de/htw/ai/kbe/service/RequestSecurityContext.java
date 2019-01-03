package de.htw.ai.kbe.service;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

/**
 * Klasse um einen Principal im Rahmen eines Sicherheitskontexts abzulegen
 * @author camilo, jns
 *
 */
public class RequestSecurityContext implements SecurityContext {
	// source: https://simplapi.wordpress.com/2015/09/19/jersey-jax-rs-securitycontext-in-action/
	
	private String username;
	
	public RequestSecurityContext(String username) {
		this.username = username;
	}

	/**
	 * Methode um den Namen des sich authorifizierenden Users abzulegen
	 */
	@Override
	public Principal getUserPrincipal() {
		return new Principal() {
			@Override
			public String getName() {
				return username;
			}
		};
	}

	// nicht implementiert, da für uns keinen Nutzen
	@Override
	public boolean isUserInRole(String role) {
		return false;
	}

	// nicht implementiert, da für uns keinen Nutzen
	@Override
	public boolean isSecure() {
		return true;
	}

	// nicht implementiert, da für uns keinen Nutzen
	@Override
	public String getAuthenticationScheme() {
		return null;
	}

}
