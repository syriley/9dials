package models;
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
import play.libs.Crypto;

import com.google.gson.JsonObject;
 
@Entity
public class AUser extends Model {
 	
	public String name;
	public String username;
	public String email;
	public String password;
	public String bio;
	private String imageUrl;
	public boolean isEmailVerified = true;
	
	@ManyToMany
	public List<Instrument> instruments;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	public List<UserSession> userSessions;
	
	public AUser(){
	}
	
	public AUser (String name, String email, String password, String bio) {
		System.out.println("Creating new user");
		this.name = name;
		this.email = email;
		this.bio = bio;
		this.password = password;
		userSessions = new ArrayList<UserSession>();
		instruments = new ArrayList<Instrument>();
	}
	
	public AUser addInstrument (Instrument instrument) {
		instrument.save();
		instruments.add(instrument);
		return this;
	}
	
	public static AUser connect(String email, String password) {
	    return find("byEmailAndPassword", email, Crypto.passwordHash(password)).first();
	}
	
	public static AUser findByUsername(String username) {
		return AUser.find("byUsername", username).first();
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
			sessions.add(userSession.session);
		}
		return sessions;
	}
}