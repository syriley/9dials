package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Instrument  extends Model{
	
	@ManyToOne
	public User user;
	public String name;
	
	public Instrument(String name, User user) {
		this.name = name;
		this.user=user;
	}
}
