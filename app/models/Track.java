package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Track extends Model{
	public long trackId;
	@ManyToOne
	public Session session;
	public String name;
	public Integer gain;
	@OneToMany
	public List<Region> regions;
	public Date modified;
	
	public Track() {
		regions = new ArrayList<Region>();
	}
	
	public static List<Track> getTracks(long sessionId) {
		List<Track> aggregatedTracks = new ArrayList<Track>();
		
		List<Track> tracks = Track.find("select t from Track t, Session s where t.session = s and s.sessionId = ? order by trackId, t.modified", sessionId).fetch();
		Track currentTrack = null;
		for (Track track : tracks) {
			if(currentTrack == null || track.trackId != currentTrack.trackId){
				currentTrack = new Track();
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
	

}
