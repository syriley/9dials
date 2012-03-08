package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Collective extends Model{
	public String description;
	public String name;
}
