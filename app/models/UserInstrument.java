package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class UserInstrument  extends Model{
	
	@ManyToOne
	public User user;
	@ManyToOne
	public Instrument instrument;
	
	public UserInstrument(Instrument instrument, User user) {
		this.instrument= instrument;
		this.user=user;
	}
}
