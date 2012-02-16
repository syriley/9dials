package models;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Track {
	public String name;
	@Embedded
	List<Region> regions;
	
	public Track() {
		regions = new ArrayList<Region>();
	}
	
	public Region addRegion() {
		Region region = new Region();
		regions.add(region);
		return region;
	}
}
