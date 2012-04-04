package controllers;

import java.util.List;

import models.DrumLesson;
import models.DrumLessonGroup;

import play.mvc.Controller;

public class DrumLessons extends Controller{
    public static void index() {
        List<DrumLessonGroup> drumLessonGroups = DrumLessonGroup.findAll();
        render(drumLessonGroups);
    }
    
    public static void saveLesson(long id, String name, String youtubeLocation) {
        DrumLesson lesson = DrumLesson.findById(id);
        lesson.name = name;
        lesson.youtubeLocation = youtubeLocation;
        lesson.save();
        index();
    }
}
