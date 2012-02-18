package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Instrument  extends Model{
	
	public String name;
	
	public Instrument(String name) {
		this.name = name;
	}
}
