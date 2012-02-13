package controllers;

import models.User;
import play.mvc.Controller;

public class Profile extends Controller {
	public static void index() {
		
    User user= User.findById(new Long(1));
    
    //Session session = new Session();
    //System.out.println("session name " + session.name);
    //session.work = "sdfsdfsd";
    //String name = session.name;
    render(user);
    
	}
	public static void edit() {
		 render();
	 }
}
