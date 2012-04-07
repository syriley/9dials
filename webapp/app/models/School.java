package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class School extends Model {
    
    public String name;
    public String email;
    public String website;
    public boolean hasReplied;
    public boolean hasLinked;
    public double longitude;
    public double latitude;
    
    public School(String name, String email, String website,
            boolean hasReplied, boolean hasLinked, double longitude,
            double latitude) {
        super();
        this.name = name;
        this.email = email;
        this.website = website;
        this.hasReplied = hasReplied;
        this.hasLinked = hasLinked;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    
}
