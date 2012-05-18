package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.gson.JsonObject;

import play.db.jpa.Model;

@Entity
public class Session extends Model{
	
	public Date modified;
	public String name;
	public String description;
	public String access;
	public String data;
	
	@OneToMany(mappedBy="session", cascade=CascadeType.ALL)
	public List<UserSession> userSessions;
	
	
	public Session() {
		userSessions = new ArrayList<UserSession>();
		this.data= "{ " +
		                "version: 0.1," +
		                "name: \"Untitled\", " +
		                "sample_rate: 48000," +
		                "tracks:  []," +
		                "playhead: {" +
		                    "position: 0" +
		                "}" +
	                "}";
	}
	
	public void shareWithUser(User user) {
		new UserSession(user, this, "collaborator").save();
	}
	
	public void removeUser(Long userId) {
		UserSession userSession = UserSession.findByUserAndSession(userId, this.id);
		userSession.delete();
	}
	
	public static Session findByName(String name) {
	    return Session.find("byName", name).first();
	}
	
	public List<UserSession> getUserSessions() {
		return UserSession.getSharedUserSessions(this);
	}
}
