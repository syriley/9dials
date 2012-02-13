package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class UserSession extends Model{
	
	//@OneToOne
	//SessionRole sessionRole;
	@ManyToOne
	public User user;
	@ManyToOne
	public Session session;
	
	public UserSession(User user, Session session) {
		this.user = user;
		this.session = session;
	}
}
