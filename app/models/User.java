package models;
 
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
 
@Entity
public class User extends Model {
 	
	public String name;
	public String email;
	public String address;
	public String bio;
	@OneToMany(mappedBy="user", targetEntity= Instrument.class)
	public List<Instrument> instruments;
 	
}