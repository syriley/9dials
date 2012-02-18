package models;
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;
import play.mvc.Scope.Flash;

import com.google.gson.JsonObject;

import controllers.Profile;
 
@Entity
public class User extends Model {
 	
	public String name;
	public String email;
	public String password;
	public String bio;
	private String imageUrl;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	public List<UserInstrument> instruments;
	
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
		instruments = new ArrayList<UserInstrument>();
	}
	
	public User addInstrument (String name) {
		Instrument instrument = Instrument.find("name=?",name).first();
		if(instrument==null){
			instrument = new Instrument(name).save();
		}
		UserInstrument userInstrument= new UserInstrument(instrument,this).save();
		instruments.add(userInstrument);
		return this;
	}
	
	public static User connect(String email, String password) {
	    return find("byEmailAndPassword", email, password).first();
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
}