package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Instrument;
import models.AUser;
import play.mvc.Controller;

public class Users extends Controller{

	public static final int MAX_ITEMS = 10;
	public static void list(String term){
		List<String> instrumentNames = filterUsers(term);
		renderJSON(instrumentNames);
	}

	public static List<String> filterUsers(String term){
		List<AUser> matchedUsers = findMatchingUsers(term);		
		List<String> userNames = extractUserNames(matchedUsers);
		return userNames;
	}			
	
	private static List<AUser> findMatchingUsers(String term) {
		List<AUser> allUsers = Collections.emptyList();
		if(term==null|| term.equals("")){
			allUsers= AUser.find("order by username").fetch(MAX_ITEMS);
		}
		else{
			allUsers= AUser.find("username like ? order by username","%"+term+"%").fetch(MAX_ITEMS);	
		}
		return allUsers;
	}

	private static List<String> extractUserNames(
			List<AUser> allUsers) {
		if(allUsers.isEmpty()){
			return Collections.emptyList();
		}
		List<String> userNames = new ArrayList<String>();
		for(AUser i : allUsers){
			userNames.add(i.username);
		}
		return userNames;
	}
}
