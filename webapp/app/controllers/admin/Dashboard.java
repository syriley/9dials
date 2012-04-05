package controllers.admin;

import java.util.List;

import controllers.LoggedInController;

import models.DrumLesson;
import models.DrumLessonGroup;
import play.mvc.Controller;

public class Dashboard extends LoggedInController{
	 public static void index() {
	     List<DrumLessonGroup> drumLessonGroups = DrumLessonGroup.findAll();
		 render(drumLessonGroups);
	 }
	 
	 public static void showLesson(String name) {
         DrumLesson lesson = DrumLesson.findByName(name);
         render(lesson);
     }
	 
	 public static void editLesson(String name) {
	         DrumLesson lesson = DrumLesson.findByName(name);
	         render(lesson);
	     }

}
