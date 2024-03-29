package controllers;

import models.User;

public class Security extends Secure.Security {
	 
    static boolean authentify(String username, String password) {
    	return User.connect(username, password) != null;
    }
    static void onAuthenticated() {
    	redirect("/sessions");
    }
    
    public static String connected() {
        return Security.connected();
    }
    
}