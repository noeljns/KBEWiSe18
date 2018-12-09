package de.htw.ai.kbe.user;

import java.util.Random;

/**
 * Klasse die ein Authentifizierungs Token repräsentiert
 * @author camilo, jns
 *
 */
public class AuthToken {

	protected String token;

	AuthToken(User user) {
		Random random = new Random();
		this.token =  user.getUserId().hashCode() + Integer.toString(random.nextInt());
	}
	
	public String getTokenStr() {
		return token;
	}
}
