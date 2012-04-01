package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class DrumLessonGroup extends Model {
    
    public String name;
    public @OneToMany(mappedBy="drumLessonGroup", cascade=CascadeType.ALL) 
    List<DrumLesson> lessons;
}
