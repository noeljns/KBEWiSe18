package de.htw.ai.kbe.user;

/**
 * Klasse die eine/n User/in repräsentiert
 * @author camilo, jns
 *
 */
public class User {
	
	private Integer id;
	private String unsername;
	private String lastName;
	private String firstName;
	private AuthToken token;
	
	/**
	 * Konstruktor
	 */
	public User() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return unsername;
	}

	public void setUsername(String userId) {
		this.unsername = userId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Methode, um Token für den User zu generieren
	 * @return generierten token
	 */
	public AuthToken getToken() {
		// User hat noch keinen token, daher ist token aktuell noch null
		// ansonsten hat User bereits einen token, dieser wird dann direkt retuniert
		if (token == null) {
			token = new AuthToken(this);
		}
		
		// debug user for JUnit Test
		if (this.unsername.equals("debugUser")){
            return new DebugToken(this);
        }
		
		return token;
	}

	public void setToken(AuthToken token) {
		this.token = token;
	}

}
