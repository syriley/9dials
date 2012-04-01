package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class DrumLesson extends Model{

    public String lessonName;
    public String pdfLocation;
	public String youTubeLocation;
	@ManyToOne 
	public DrumLessonGroup drumLessonGroup;

    public DrumLesson(String lessonName, String pdfLocation,
            String youTubeLocation, DrumLessonGroup drumLessonGroup) {
        super();
        this.lessonName = lessonName;
        this.pdfLocation = pdfLocation;
        this.youTubeLocation = youTubeLocation;
        this.drumLessonGroup = drumLessonGroup;
    }
}
