package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
import controllers.admin.UserSessions;

@Entity
public class UserSession extends Model{
	
	//@OneToOne
	//SessionRole sessionRole;
	@ManyToOne
	public User user;
	@ManyToOne
	public Session session;
	public String role; 
	
	public UserSession(User user, Session session, String role) {
		this.user = user;
		this.session = session;
		this.role = role;
	}
	
	public static List<UserSession> getSharedUserSessions(long sessionId) {
		return UserSession.find("session.id = ?", sessionId).fetch();
	}
}
