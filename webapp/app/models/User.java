package models;
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.libs.Crypto;
import util.JsonUtils;
import models.Track;
import com.google.gson.JsonObject;

import dtos.SessionDto;
import dtos.UserDto;
 
@Entity
@Table(name="auser")
public class User extends Model {
 	
	public String name;
	public String username;
	public String email;
	public String password;
	public String bio;
	private String imageUrl;
	public String externalId;
	public String externalProvider;
	public boolean isEmailVerified = true;
	
	@ManyToMany
	public List<Instrument> instruments;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	public List<UserSession> userSessions;
	
	public User(){
	}
	
	public User (String name, String email, String password, String bio) {
		System.out.println("Creating new user");
		this.name = name;
		this.email = email;
		this.bio = bio;
		this.password = password;
		userSessions = new ArrayList<UserSession>();
		instruments = new ArrayList<Instrument>();
	}
	
	public User addInstrument (Instrument instrument) {
		instrument.save();
		instruments.add(instrument);
		return this;
	}
	
	public static User connect(String email, String password) {
	    return find("byEmailAndPassword", email, Crypto.passwordHash(password)).first();
	}
	
	public static User findByUsername(String username) {
		return User.find("byUsername", username).first();
	}
	
	public void createSession(Session session) {
		String role ="owner";
		UserSession userSession = new UserSession(this, session, role).save();
		userSessions.add(userSession);
		session.userSessions.add(userSession);
	}
	
	public String getImageUrl(){
		if(imageUrl==null){
			return "/public//images/default_avatar.jpg";
		}
		else{
			return imageUrl;
		}
	}
	
	public void setImageUrl(String imageUrl){
		this.imageUrl=imageUrl;
	}
	
	public static void facebookOAuthCallback(JsonObject data){
		OAuthUserHelper.oAuthCallback(data);
	}

	public List<Session> getSessions() {
		List<Session> sessions = new ArrayList<Session>();
		for (UserSession userSession : userSessions ) {
		    if(userSession.session.enabled) {
		        sessions.add(userSession.session);
		    }
		}
		return sessions;
	}
	
	public List<SessionDto> getSessionDtos() {
       List<Session> sessions = getSessions();
       List<SessionDto> sessionDtos = new ArrayList<SessionDto>();
       for (Session session : sessions) {
           sessionDtos.add(new SessionDto(session.modified,
                   session.name, 
                   session.description, 
                   session.access, 
                   session.data, 
                   session.enabled));
       }
        return sessionDtos;
    }
	
	public UserDto getDto(){
	    return new UserDto(name, 
	            username, 
	            email, 
	            password, 
	            bio, 
	            imageUrl, 
	            externalId, 
	            externalProvider, 
	            isEmailVerified);
	}
}
