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
	public List<UserInstrument> instruments;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	public List<UserSession> userSessions;
	
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
}