package controllers.admin;

import java.util.List;

import controllers.LoggedInController;

import models.DrumLesson;
import models.DrumLessonGroup;
import models.School;
import play.mvc.Controller;

public class Directory extends LoggedInController{
	 public static void index() {
	     List<School> schools = School.findAll();
	     render(schools);
	 }
	 
	 public static void add(String name, 
	                         String email, 
	                         String website, 
	                         boolean hasReplied, 
	                         boolean hasLinked,
	                         double longitude,
	                         double latitude) {
	     School school = new School(name, email, website, hasReplied, hasLinked, longitude, latitude);
	     school.save();
         index();
     }
}
