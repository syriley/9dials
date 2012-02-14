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
	
}
