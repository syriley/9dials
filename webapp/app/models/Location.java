package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Location extends Model {
    public String city;
    public String country;
    public Date dateProcessed;
    
    public static List<Location> findUnprocessed() {
        return Location.find("dateProcessed is null").fetch();
    }
}
