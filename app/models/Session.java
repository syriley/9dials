package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Session extends Model{
	
	public Long sessionId;
	public Date modified;
	public String name;
	public String description;
	public String access;
	
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
	
	public static Session getSession(long sessionId) {
		Session aggregatedSession = new Session();
		
		List<Session> sessions = Session.find("sessionId = ? order by modified", sessionId).fetch();
		for (Session session : sessions) {
			if(session.name != null) {
				aggregatedSession.name = session.name;
			}
			
			if(session.description != null) {
				aggregatedSession.description = session.description;
			}
			aggregatedSession.id = session.id;
		}
		return aggregatedSession;
	}
	
	public static Session updateSession(long id, Session session) {
		Session originalSession = Session.findById(id);
		Session deltaSession = new Session();
		deltaSession.sessionId = originalSession.sessionId;
		if (originalSession.name != session.name) {
			deltaSession.name = session.name;
		}
		
		if (originalSession.description != session.description) {
			deltaSession.description = session.description;
		}
		deltaSession.save();
		return deltaSession;
	}
	
	@Override
	public boolean equals(Object other) {
		Session session = (Session)other;
		return id == session.id;
	}

	public void shareWithUser(AUser user) {
		new UserSession(user, this, "collaborator").save();
	}
	
	public void removeUser(Long userId) {
		UserSession userSession = UserSession.findByUserAndSession(userId, this.id);
		userSession.delete();
	}
}
