package controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import models.DrumLesson;
import models.DrumLessonGroup;
import play.data.validation.Required;
import play.mvc.Controller;

public class DrumLessons extends Controller{
    public static void index() {
        List<DrumLessonGroup> drumLessonGroups = DrumLessonGroup.findAll();
        render(drumLessonGroups);
    }
    
    public static void showLesson(long id) {
        DrumLesson lesson = DrumLesson.findById(id);
        render(lesson);
    }
    
    public static void editLesson(long id) {
        DrumLesson lesson = DrumLesson.findById(id);
        if (lesson == null) {
            lesson = new DrumLesson(); 
        }
        render(lesson);
    }
    
    public static void saveLesson(long id,@Required String name, String youtubeLocation) {
        
        DrumLesson lesson = DrumLesson.findById(id);
        
        lesson.name = name;
        
        Pattern watchPattern = Pattern.compile("www.youtube.com/watch\\?v=([a-zA-Z1-9]+)&");
        Matcher matcher = watchPattern.matcher(youtubeLocation);
        if (matcher.find()) {
            youtubeLocation = matcher.group(1);
            lesson.youtubeLocation = youtubeLocation;
        }
        
        if(StringUtils.isEmpty(youtubeLocation)) {
            Pattern embedPattern = Pattern.compile("www.youtube.com/embed/(.*)\\?");
            matcher = embedPattern.matcher(youtubeLocation);
            if(matcher.find()) {
                youtubeLocation = matcher.group(1);
        
            }
        }
        //TODO:add user action
        lesson.save();
        showLesson(id);
    }
}
