package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import play.db.jpa.Model;
import controllers.admin.UserSessions;

@Entity
public class UserSession extends Model{
	
	//@OneToOne
	//SessionRole sessionRole;
	@ManyToOne
	public User user;
	public Session sessionId;
	@Transient
	public Session session;
	public String role; 
	
	public UserSession(User user, Session session, String role) {
		this.user = user;
		this.session = session;
		this.role = role;
	}
	
//	public static UserSession findByUserAndSession(long userId,  long sessionId) {
//		String queryString = "select us " +
//				"from UserSession us " +
//				"where us.session.id = ? " +
//				"and us.user.id = ?";
//		return UserSession.find(queryString, sessionId, userId).first();
//	}
	
	public static List<UserSession> getSharedUserSessions(Object sessionId) {
		return UserSession.find("session.id = ?", sessionId).fetch();
	}
}
