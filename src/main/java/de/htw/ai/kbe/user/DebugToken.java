package de.htw.ai.kbe.user;

public class DebugToken extends AuthToken {
	private String token;
	public static final String DEBUG_TOKEN = "debugToken";

	public DebugToken(User user) {
		super(user);
		this.token = DEBUG_TOKEN;
	}

	@Override
	public String getTokenStr() {
		return this.token;
	}

}
