package controllers;

import models.UserInstrument;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class Profile extends LoggedInController {
	
	public static void index() {
		render();
	}
	public static void edit() {
		 render();
	 }
	
	public static void addInstrument (String name) {		
		User user = User.find("byEmail", Security.connected()).first();
		user.addInstrument(name);
		user.save();
        edit();
        
	}
}
