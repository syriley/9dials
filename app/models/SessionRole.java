package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class SessionRole extends Model{
	
	public String name;
	@OneToOne
	Session session;
}
