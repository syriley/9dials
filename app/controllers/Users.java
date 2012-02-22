package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Instrument;
import models.User;
import play.mvc.Controller;

public class Users extends Controller{

	public static final int MAX_ITEMS = 10;
	public static void list(String term){
		List<String> instrumentNames = filterUsers(term);
		renderJSON(instrumentNames);
	}

	public static List<String> filterUsers(String term){
		List<User> matchedUsers = findMatchingUsers(term);		
		List<String> userNames = extractUserNames(matchedUsers);
		return userNames;
	}			
	
	private static List<User> findMatchingUsers(String term) {
		List<User> allUsers = Collections.emptyList();
		if(term==null|| term.equals("")){
			allUsers= User.find("order by username").fetch(MAX_ITEMS);
		}
		else{
			allUsers= User.find("username like ? order by username","%"+term+"%").fetch(MAX_ITEMS);	
		}
		return allUsers;
	}

	private static List<String> extractUserNames(
			List<User> allUsers) {
		if(allUsers.isEmpty()){
			return Collections.emptyList();
		}
		List<String> userNames = new ArrayList<String>();
		for(User i : allUsers){
			userNames.add(i.username);
		}
		return userNames;
	}
}
