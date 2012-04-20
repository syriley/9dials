package controllers.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import models.School;
import models.User;
import controllers.LoggedInController;
import controllers.Security;
import play.data.validation.Error;

public class Directory extends LoggedInController{
    
	 public static void index(int start, int size, String originalSearch) {
	     if (size == 0) {
	         size = 40;
	     }
	     String search = originalSearch;
	     if (StringUtils.isEmpty(search)) {
	         search = "%";
	     }
	     else {
	         search = "%"+ search +"%";
	     }
	     search = search.toLowerCase();
	     List<School> schools = School.find("website like ? or email like ? order by haveEmailed desc, email desc", search,search).fetch(start,size);
	     List<School> notEmailed = School.find("haveEmailed = ?", false).fetch();
	     int count = School.find("website like ? or email like ?", search, search).fetch().size();
	     int numberOfPager = (int)Math.ceil(count/size) + 2;
	     List<Integer> pages = new ArrayList<Integer>();
	     for (int i = 1; i < numberOfPager; i++) {
	         pages.add(i);
	     }
	     render(schools, notEmailed, pages, originalSearch);
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
	         index(0,0, "");
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
        index(0,0,"");
    }
    
    public static void updateLinked(long id, boolean hasLinked) {
        School school = School.findById(id);
        school.hasLinked = hasLinked;
        school.save();
        index(0,0,"");
    }
    
    public static void markAsEmailed() {
        User currentUser = User.findByUsername(Security.connected());
        School.markAllAsEmailed(currentUser);
        index(0,0,"");
    }
    
    public static void show(long id) {
        School school = School.findById(id);
        render(school);
    }
    
    public static void update(long id, String name, String firstName, String lastName) {
        School school = School.findById(id);
        school.name = name;
        school.firstName = firstName;
        school.lastName = lastName;
        school.save();
        show(id);
    }

    public static void updatePrimaryEmail(long id, String emailAddress) {
        School school = School.findById(id);
        school.email = emailAddress;
        school.save();
        show(id);
    }
    
    public static void addPossibleEmail(long id, String emailAddress) {
        School school = School.findById(id);
        school.possibleEmails.add(emailAddress);
        school.save();
        show(id);
    }
}
