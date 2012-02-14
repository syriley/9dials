package controllers;

import java.util.List;

import models.Session;
import models.User;

public class Collaborate extends LoggedInController {
	public static void index(Long id) {
		Session seshion = Session.findById(id);
		List<User> usersToShare = User.findAll();
		render(seshion, usersToShare);
	}
}