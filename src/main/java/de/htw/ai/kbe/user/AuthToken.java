package de.htw.ai.kbe.user;

import java.util.Random;

public class AuthToken {
	private String token;
	private User user;
	
	public AuthToken(User user) {
		Random random = new Random();
		this.token = random.ints(30).toString();
	}

	public String getToken() {
		return token;
	}
}
