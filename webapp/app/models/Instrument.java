package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.db.jpa.Model;

@Entity
public class Instrument  extends Model{
	
	@ManyToMany(mappedBy="instruments")
	public List<User> users;
	public String name;
	
	public Instrument(String name) {
		this.name = name;
	}
}
