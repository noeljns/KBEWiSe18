package de.htw.ai.kbe.user;

public class User {
	
	private Integer id;
	private String userid;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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
		if(token == null) {
			token = new AuthToken(this);
		}
		 
		return token;
	}

	public void setToken(AuthToken token) {
		this.token = token;
	}

}
