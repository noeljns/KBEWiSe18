package de.htw.ai.kbe.user;

import java.util.Random;

public class AuthToken {

	protected String token;

	AuthToken(User user) {
		Random random = new Random();
		this.token =  user.getUserId().hashCode() + Integer.toString(random.nextInt());
		System.out.println("Token for user " + user.getUserId() + " has been created: " + token);
		System.out.println();
	}

	public String getTokenStr() {
		return token;
	}
}
