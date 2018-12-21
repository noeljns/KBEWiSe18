package de.htw.ai.kbe.user;

/**
 * Klasse die ein Debugging und Testing Authentifizierungs Token repräsentiert
 * @author camilo, jns
 *
 */
public class DebugToken extends AuthToken {
	private String token;
	public static final String DEBUG_TOKEN = "debugToken";

	public DebugToken(SongRXUser user) {
		super(user);
		this.token = DEBUG_TOKEN;
	}

	@Override
	public String getTokenStr() {
		return this.token;
	}

}
