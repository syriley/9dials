package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.google.gson.JsonObject;

import play.db.jpa.Model;

@Entity
public class Session extends Model{
	
	public Date modified;
	public String name;
	public String description;
	public String access;
	@Lob
	public String data;
	
	@OneToMany(mappedBy="session", cascade=CascadeType.ALL)
	public List<UserSession> userSessions;
	
	public Session() {
		this(null,null);
	}
	public Session(String name, String description) {
		userSessions = new ArrayList<UserSession>();
		this.name = name;
		this.description = description;
	}
	
	public void shareWithUser(AUser user) {
		new UserSession(user, this, "collaborator").save();
	}
	
	public void removeUser(Long userId) {
		UserSession userSession = UserSession.findByUserAndSession(userId, this.id);
		userSession.delete();
	}
}
