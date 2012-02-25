package models;
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

import com.google.gson.JsonObject;
 
@Entity
public class User extends Model {
 	
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
	    return find("byEmailAndPassword", email, password).first();
	}
	
	public Session getSession(long sessionId) {
		for (UserSession userSession : userSessions) {
			if (userSession.branch.session.getId().equals(sessionId)) {
				Session session = userSession.branch.session;
				session.currentBranch = userSession.branch;
				session.currentBranch.getTracks();
				return session;
			}
		}
		return null;
	}
	
	public void createSession(Session session) {
		Branch branch = new Branch(session).save();
		String role ="owner";
		UserSession userSession = new UserSession(this, branch, role);
		userSessions.add(userSession);
		session.currentBranch = branch;
		session.addTrack();
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
}