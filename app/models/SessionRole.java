package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class SessionRole extends Model{
	public String name;
}
