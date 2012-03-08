package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Track extends Model {

	public String audioUrl;
	public String provider;
	
}
