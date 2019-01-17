package de.htw.ai.kbe.user;

import java.util.Random;

import de.htw.ai.kbe.bean.SongRXUser;

/**
 * Klasse die ein Authentifizierungs Token repräsentiert
 * @author camilo, jns
 *
 */
public class AuthToken {

	protected String token;

	public AuthToken(SongRXUser user) {
		// TODO token stabil halten oder token nicht transient annotieren und in datenbank ablegen
		//Random random = new Random();
		this.token =  Integer.toString(user.getUsername().hashCode());
		//this.token =  user.getUsername().hashCode() + Integer.toString(random.nextInt());
	}
	
	public String getTokenStr() {
		return token;
	}
}
