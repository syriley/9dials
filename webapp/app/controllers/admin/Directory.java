package controllers.admin;

import java.util.List;

import models.School;
import models.User;
import controllers.LoggedInController;
import controllers.Security;
import play.data.validation.Error;

public class Directory extends LoggedInController{
    
	 public static void index() {
	     List<School> schools = School.findAll();
	     List<School> notEmailed = School.find("haveEmailed = ?", false).fetch();
	     render(schools, notEmailed);
	 }
	 
	 public static void add(String name,
             String email,
             String website,
             boolean haveEmailed,
             boolean hasReplied,
             boolean hasLinked,
             double longitude,
             double latitude) {
	     validation.required(name);
	     validation.required(email);
	     validation.required(website);
	     if(!validation.hasErrors()) {
	         (new School(name, email, website, haveEmailed, hasReplied, hasLinked, longitude, latitude)).save();
	         index();
	     }
	     else {
	         List<School> schools = School.findAll();
	         List<School> notEmailed = School.find("haveEmailed = ?", false).fetch();
	         renderTemplate("admin/Directory/index.html",schools, notEmailed, validation.errors());
	     }
        
    }
    
    public static void updateReplied(long id, boolean hasReplied) {
        School school = School.findById(id);
        school.hasReplied = hasReplied;
        school.save();
        index();
    }
    
    public static void updateLinked(long id, boolean hasLinked) {
        School school = School.findById(id);
        school.hasLinked = hasLinked;
        school.save();
        index();
    }
    
    public static void markAsEmailed() {
        User currentUser = User.findByUsername(Security.connected());
        School.markAllAsEmailed(currentUser);
        index();
    }
}
