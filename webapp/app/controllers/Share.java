package controllers;

import models.Source;
import play.mvc.Controller;

public class Share extends Controller {
	public static void index(String sourceId) {
		String s3url = Source.getFileUrl(sourceId);
		redirect(s3url);
	}
}
