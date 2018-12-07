package de.htw.ai.kbe.user;

import java.util.Random;
import java.util.stream.IntStream;

public class AuthToken {
	private String token;
	private User user;
	
	public AuthToken(User user) {
		Random random = new Random();
		// this.token = "[" + user.getUserId() + "]";
		this.token = user.getUserId();
		System.out.println("this is the valid token: " + token + "\n");
		// this.token =  user.getUserId().hashCode() + random.ints(30).toString();
	}

	public String getTokenStr() {
		return token;
	}
}
