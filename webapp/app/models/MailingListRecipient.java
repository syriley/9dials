package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class MailingListRecipient extends Model{

	
	public MailingListRecipient(String email) {
		super();
		this.email = email;
	}
	
	public String email;
}
