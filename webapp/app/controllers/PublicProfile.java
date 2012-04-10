package controllers;

import models.User;
import play.mvc.Controller;

public class PublicProfile extends Controller {
		
	public static void profile(String username) {
		User user = User.find("byUsername", username).first();
		if(user==null){
			notFound();
		}        
		renderArgs.put("publicuser", user);
		render();
	}
}
