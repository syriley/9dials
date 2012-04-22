package controllers;

import models.Source;
import play.mvc.Controller;

public class MyClips extends Controller{

	public static void index(String sourceId) {
		Source source = Source.find("byS3key", sourceId).first();
		render(source);
	}

}
