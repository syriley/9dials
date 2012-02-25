package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import play.db.jpa.JPABase;
import play.db.jpa.Model;

@Entity
public class Session extends Model{
	
	public Long sessionId;
	public String name;
	public String description;
	public Date modified;
	@Transient
	public Branch currentBranch;
	@OneToOne
	public Version version;
	@OneToMany(mappedBy="session", cascade=CascadeType.ALL)
	public List<Branch>branches;
	
	public Session() {
		this(null,null);
	}
	
	public Session(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void removeUser(Long userId) {
		UserSession userSession = UserSession.findByUserAndBranch(userId, this.id);
		userSession.delete();
	}
	
	public List<Track> getTracks() {
		return currentBranch.tracks;
	}

	public void addTrack() {
		addTrack("untitled");
	}
	
	public void addTrack(String name) {
		currentBranch.addTrack(name);
	}
}
