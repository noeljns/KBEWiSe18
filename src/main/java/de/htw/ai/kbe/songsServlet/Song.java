package de.htw.ai.kbe.songsServlet;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "song")
public class Song {

    private Integer id;
	private String title;
	private String artist;
	private String album;
	private Integer released;

	public Song() {    
	}

	public Song(Integer id, String title, String artist, String album, Integer released) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.released = released;
    }
	
	public Song(String title, String artist, String album, Integer released) {
        this.id = -1;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.released = released;
    }
	
	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setAlbum() {
		this.album = album;
	}
	
	public Integer getReleased() {
		return released;
	}
	
	public void setReleased(int released) {
		this.released = released;
	}

    @Override
    public String toString() {
        return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
                + released + "]";
    }
    
    public String songToJson() {
        Map<String,String> payload = new HashMap<>();
        payload.put("id", String.valueOf(this.getId()));
        payload.put("title", this.getTitle());
        payload.put("artist", this.getArtist());
        payload.put("album", this.getAlbum());
        payload.put("released", String.valueOf(this.getReleased()));
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
	
}


















