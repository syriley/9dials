package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Session extends Model{
	
	@OneToMany(mappedBy="session", cascade=CascadeType.ALL)
	public List<UserSession> userSessions;
	public String name;
	public String description;
	
	public Session(String name, String description) {
		userSessions = new ArrayList<UserSession>();
		this.name = name;
		this.description = description;
	}
	
	@Override
	public boolean equals(Object other) {
		Session session = (Session)other;
		return id == session.id;
	}

	public void shareWithUser(User user) {
		new UserSession(user, this, "collaborator").save();
	}
	
	public void removeUser(Long userId) {
		UserSession userSession = UserSession.findByUserAndSession(userId, this.id);
		userSession.delete();
	}
}
