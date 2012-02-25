package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.JPABase;
import play.db.jpa.Model;

@Entity
public class Track extends Model{
	public long trackId;
	@OneToOne
	public Version version;
	@ManyToOne
	public Branch branch;
	public String name;
	
	@OneToMany
	public List<Region> regions;
	public boolean enabled = true;
	public Integer gain;
	
	public Date modified;
	
	public Track(Branch branch) {
		
		this(branch, "untitled");
	}
	
	public Track(Branch branch, String name) {
		this.branch = branch;
		regions = new ArrayList<Region>();
		this.name = name;
	}

	@Override
	public <T extends JPABase> T save() {
		version = new Version().save();
		return super.save();
	}
}
