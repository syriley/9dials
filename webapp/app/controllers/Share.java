package controllers;

import play.mvc.Controller;

public class Share extends Controller {
	public static void index(String sourceId) {
		String s3url = Sources.getFileUrl(sourceId);
		redirect(s3url);
	}
}
