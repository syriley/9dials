package models;

import java.util.List;

import javax.persistence.Entity;

import controllers.Security;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class School extends Model {
    
    public String name;
    @Required
    public String email;
    @Required
    public String website;
    public boolean haveEmailed;
    public boolean hasReplied;
    public boolean hasLinked;
    public double longitude;
    public double latitude;
    
    public School(String name, String email, String website,
            boolean haveEmailed, boolean hasReplied, boolean hasLinked, double longitude,
            double latitude) {
        super();
        this.name = name;
        this.email = email;
        this.website = website;
        this.haveEmailed = haveEmailed;
        this.hasReplied = hasReplied;
        this.hasLinked = hasLinked;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static void markAllAsEmailed(User currentUser) {
        List<School> schools = School.findAll();
        for (School school : schools) {
            if(!school.haveEmailed) {
                school.haveEmailed = true;
                school.save();
                String action = "marked " + school.email + " as emailed";
                UserAction userAction = new UserAction(currentUser, action);
                userAction.save();
            }
        }
    }

    public static List<School> findAllLinked() {
        List<School> schools = School.find("hasLinked = ?", true).fetch();
        return schools;
    }
}
