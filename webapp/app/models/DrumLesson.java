package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class DrumLesson extends Model{

    public String lessonName;
    public String pdfLocation;
	public String youTubeLocation;

    public DrumLesson(String lessonName, String pdfLocation,
            String youTubeLocation) {
        super();
        this.lessonName = lessonName;
        this.pdfLocation = pdfLocation;
        this.youTubeLocation = youTubeLocation;
    }
}
