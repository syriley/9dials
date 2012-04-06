package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class DrumLesson extends Model{

    public String name;
    public String pdfLocation;
	public String youtubeLocation;
	@ManyToOne 
	public DrumLessonGroup drumLessonGroup;
	@OneToOne 
	public User user;

    public DrumLesson(String name, String pdfLocation,
            String youtubeLocation, DrumLessonGroup drumLessonGroup, User user) {
        super();
        this.name = name;
        this.pdfLocation = pdfLocation;
        this.youtubeLocation = youtubeLocation;
        this.drumLessonGroup = drumLessonGroup;
        this.user = user;
    }
    
    public static DrumLesson findByName(String name) {
        return DrumLesson.find("byName", name).first();
    }
    
}
