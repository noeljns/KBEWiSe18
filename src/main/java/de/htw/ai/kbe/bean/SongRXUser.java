package de.htw.ai.kbe.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.htw.ai.kbe.user.AuthToken;
import de.htw.ai.kbe.user.DebugToken;

/**
 * Entity-Klasse die eine/n SongRXUser/in repräsentiert
 * @author camilo, jns
 *
 */
@XmlRootElement(name = "user")
@Entity
//muss man mit Backtipps machen, weil PostgreSQL das fälschlicherweise als "song" an Hibernate übermittelt
//mit `SongRXUser` wird garantiert, dass es als Song übermittelt wird
@Table(name = "`SongRXUser`") 
public class SongRXUser {
	
	@Id
	@Column(name = "id")
	private Integer id;
	private String username;
	private String lastname;
	private String firstname;
	// TODO token wird mitgeschickt bei Json
	@Transient
	@XmlTransient
	@JsonIgnore
	private AuthToken token;
	
	/**
	 * leerer Standard-Konstruktor
	 */
	public SongRXUser() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userId) {
		this.username = userId;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastName) {
		this.lastname = lastName;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}
	
	/**
	 * Methode, um Token für den SongRXUser zu generieren
	 * @return generierten token
	 */
	public AuthToken getToken() {
		// SongRXUser hat noch keinen token, daher ist token aktuell noch null
		// ansonsten hat SongRXUser bereits einen token, dieser wird dann direkt retuniert
		if (token == null) {
			token = new AuthToken(this);
		}
		
		// debug user for JUnit Test
		if (this.username.equals("debugUser")){
            return new DebugToken(this);
        }
		
		return token;
	}

	public void setToken(AuthToken token) {
		this.token = token;
	}

}
