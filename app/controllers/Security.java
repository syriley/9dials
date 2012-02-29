package controllers;

import models.AUser;

public class Security extends Secure.Security {
	 
    static boolean authentify(String username, String password) {
    	return AUser.connect(username, password) != null;
    }
    static void onAuthenticated() {
    	redirect("/sessions");
    }
    
}