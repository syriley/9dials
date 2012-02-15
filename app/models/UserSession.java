package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

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
}
