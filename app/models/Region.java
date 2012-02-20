package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Region extends Model{
	public int regionId;
	@ManyToOne
	public Track track; 
	public Integer startPosition;
	public Date modified;
	
	public static List<Region> getRegions(long trackId) {
		List<Region> aggregatedRegions = new ArrayList<Region>();
		
		List<Region> regions = Region.find("select r from Region r, Track t where r.track = t and t.trackId = ? order by regionId, r.modified", trackId).fetch();
		Region currentRegion = null;
		for (Region region : regions) {
			if(currentRegion == null || region.regionId != currentRegion.regionId){
				currentRegion = new Region();
				currentRegion.regionId = region.regionId;
				aggregatedRegions.add(currentRegion);
			}
			
			if(region.startPosition != null) {
				currentRegion.startPosition = region.startPosition;
			}
		}
		return aggregatedRegions;
	}
}
