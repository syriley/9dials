package controllers;

import java.util.List;

import models.DrumLesson;
import models.DrumLessonGroup;
import models.School;
import play.mvc.Controller;

public class Schools extends Controller{
	 
    public static void index() {
        
	     List<School> schools = School.findAllLinked();
		 render(schools);
	 }
	 
}
