package models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class School extends Model {
    
    public String name;
    public String email;
    public String firstName;
    public String lastName;
    @Unique
    public String website;
    public String logoUrl;
    @ElementCollection
    public Set<String> phoneNumbers;
    
    @ElementCollection
    public Set<String> possibleEmails;
    
    @ElementCollection
    public Set<String> photoUrls;
    public String description;
    public String twitter;
    public String facebook;
    @OneToOne
    public Address address;
    public boolean haveEmailed;
    public boolean hasReplied;
    public boolean hasLinked;
    public double longitude;
    public double latitude;
    public Date updated;
    
    public School(String name, String email, String website,
            boolean haveEmailed, boolean hasReplied, boolean hasLinked, double longitude,
            double latitude) {
        this();
        this.name = name;
        this.email = email;
        this.website = website;
        this.haveEmailed = haveEmailed;
        this.hasReplied = hasReplied;
        this.hasLinked = hasLinked;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public School() {
        phoneNumbers = new HashSet<String>();
        possibleEmails = new HashSet<String>();
        photoUrls = new HashSet<String>();
        address = new Address();
    }

    public static void markBatchAsEmailed() {
        List<School> schools = School.getNextEmailBatch();
        for (School school : schools) {
            if(!school.haveEmailed) {
                school.haveEmailed = true;
                school.updated = new Date();
                school.save();
            }
        }
    }
    
    public static List<School> getNextEmailBatch() {
        return School.find("haveEmailed = ? order by email", false).fetch(25);
    }

    public static List<School> findAllLinked() {
        List<School> schools = School.find("hasLinked = ?", true).fetch();
        return schools;
    }
    
    public static List<School> findAllEmailed() {
        List<School> schools = School.find("haveEmailed = ?", true).fetch();
        return schools;
    }

    public static School findByWebsite(String website) {
        return School.find("website = ?", website).first();
    }

    public static School getSchoolWithoutEmail() {
        return School.find("email is null order by updated desc").first()    ;
    }

    @Override
    public String toString() {
        return "School [name=" + name + ", email=" + email + ", website="
                + website + ", logoUrl=" + logoUrl + ", phoneNumbers="
                + phoneNumbers + ", possibleEmails=" + possibleEmails
                + ", photoUrls=" + photoUrls + ", Description=" + description
                + ", address=" + address + ", haveEmailed=" + haveEmailed
                + ", hasReplied=" + hasReplied + ", hasLinked=" + hasLinked
                + ", longitude=" + longitude + ", latitude=" + latitude
                + ", updated=" + updated + "]";
    }
    
}
