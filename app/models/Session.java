package models;

import java.util.ArrayList;
import java.util.List;

import play.modules.morphia.Model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

@Entity
public class Session extends Model{
	
	public String name;
	public String description;
	public List<SessionUser> sessionUsers;
	//public List<Track> tracks;
	public List<Track> tracks;
	
	public Session(String name) {
		this(name, null);
	}
	public Session(String name, String description) {
		this.name = name;
		this.description = description;
		tracks = new ArrayList<Track>();
		sessionUsers = new ArrayList<SessionUser>();
	}
	
	public SessionUser addSessionUser() {
		SessionUser sessionUser = new SessionUser();
		sessionUsers.add(sessionUser);
		return sessionUser;
	}
	
	public Track addTrack() {
		Track track = new Track();
		tracks.add(track);
		return track;
	}
	
	
	@Override
	public boolean equals(Object other) {
		Session session = (Session)other;
		return getId() == session.getId();
	}

	public void shareWithUser(User user) {
		new UserSession(user, this, "collaborator").save();
	}
	
//	public void removeUser(Long userId) {
//		UserSession userSession = UserSession.findByUserAndSession(userId, this.getId());
//		userSession.delete();
//	}
}
