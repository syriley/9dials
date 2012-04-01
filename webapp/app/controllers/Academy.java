package controllers;

import java.util.List;

import models.DrumLessonGroup;
import play.mvc.Controller;

public class Academy extends Controller{
	 public static void index() {
	     List<DrumLessonGroup> drumLessonGroups = DrumLessonGroup.findAll();
		 render(drumLessonGroups);
	 }
}
