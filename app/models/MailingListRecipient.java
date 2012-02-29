package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class MailingListRecipient extends Model{

	
	public MailingListRecipient(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	public String name;
	public String email;
}
