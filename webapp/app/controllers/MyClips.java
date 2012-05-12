package controllers;

import models.Source;
import models.User;
import play.mvc.Controller;

public class MyClips extends Controller{

	public static void index(String sourceId) {
		Source source = new Source(new User(), "sdf", "sdf", "f", "");//Source.find("byS3key", sourceId).first();
		render(source);
	}

}
