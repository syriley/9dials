package models;
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
 
@Entity
public class User extends Model {
 	
	public String name;
	public String email;
	public String password;
	public String bio;
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	public List<Instrument> instruments;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	public List<UserSession> userSessions;
	
	public User (String name, String email, String password, String bio) {
		this.name = name;
		this.email = email;
		this.bio = bio;
		this.password = password;
		userSessions = new ArrayList<UserSession>();
		instruments = new ArrayList<Instrument>();
	}
	
	public User addInstrument (String name) {
		Instrument instrument = new Instrument(name).save();
		instruments.add(instrument);
		return this;
	}
	
	public static User connect(String email, String password) {
	    return find("byEmailAndPassword", email, password).first();
	}
	
	public List<Session> getSessions() {
		return Session.find().filter("sessionUsers.userId", id).asList();
	}
	
	public Session createNewSession(String name) {
		Session session = new Session(name);
		SessionUser sessionUser = session.addSessionUser();
		sessionUser.userId = id;
		sessionUser.role = "owner";
		session.save();
		return session;
	}
}