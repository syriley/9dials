package controllers;

import models.MailingListRecipient;
import models.AUser;
import play.mvc.Controller;

public class MailingList extends Controller{
	
	public static void index() {
		render();
	}
	
	public static void save(String name, String emailAddress) {
		new MailingListRecipient(name, emailAddress).save();
		redirect("/");
	}
}
