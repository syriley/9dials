package controllers;

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
}
