package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.JPABase;
import play.db.jpa.Model;

@Entity
public class Branch extends Model{
	
	@ManyToOne
	public Session session;
	@OneToMany(mappedBy="branch", cascade=CascadeType.ALL)
	public List<Track> tracks;
	@OneToOne 
	public Version version;
	@OneToOne 
	public Version branchedFromVersion;
	
	public Branch(Session session) {
		this.session = session;
		tracks = new ArrayList<Track>();
	}
	
	public void addTrack(String name) {
		new Track(this, name).save();
	}
	
	public static Session getSession(long id, long branchId) {
		Session aggregatedSession = new Session();
		Session requestedSession = Session.findById(id);
		aggregatedSession.id = requestedSession.id;
		
		List<Session> sessions = Session.find("sessionId = ? and version.id <= ? order by version.id", 
				requestedSession.sessionId, requestedSession.version.id).fetch();
		
		for (Session session : sessions) {
			if(session.name != null) {
				aggregatedSession.name = session.name;
			}
			
			if(session.description != null) {
				aggregatedSession.description = session.description;
			}
			aggregatedSession.sessionId = session.sessionId;
			
		}
		return aggregatedSession;
	}
	
	public void updateVersion() {
		save();
	}
	
	public List<Track> getTracks() {
		List<Track> aggregatedTracks = new ArrayList<Track>();
		Branch branchedFrom = Branch.find("byVersion", branchedFromVersion).first();
		List<Track> tracks = Track.find(
				"branch = ? " +
			    "and version <= ? " +
				"order by trackId, modified", this, version).fetch();

		if(branchedFrom != null) {
			List<Track> branchedFromTracks = Track.find(
					"branch = ? " +
						"and version < ? " +
					"order by trackId, modified", branchedFrom, branchedFrom.version).fetch();
	
			tracks.addAll(branchedFromTracks);
		}
		Track currentTrack = null;
		
		for (Track track : tracks) {
			if(currentTrack == null || track.trackId != currentTrack.trackId){
				currentTrack = new Track(this);
				currentTrack.trackId = track.trackId;
				aggregatedTracks.add(currentTrack);
			}
			
			if(track.name != null) {
				currentTrack.name = track.name;
			}
			
			if(track.gain != null) {
				currentTrack.gain = track.gain;
			}
		}
		
		for (Track track : aggregatedTracks) {
			track.regions = Region.getRegions(track.trackId);
		}
		return aggregatedTracks;
	}
	
	public Session updateBranch(long branchId) {
		return null;
//		Session originalSession = Session.getSession(id, branchId);
//		Session deltaSession = new Session();
//		deltaSession.sessionId = sessionId;
//		if (originalSession.name != name) {
//			deltaSession.name = name;
//		}
//		
//		if (originalSession.description != description) {
//			deltaSession.description = description;
//		}
//		return deltaSession.save();
	}
	
	public void shareWithUser(User user) {
		new UserSession(user, this, "collaborator").save();
	}
	
	@Override
	public <T extends JPABase> T save() {
		version = new Version().save();
		return super.save();
	}

}
