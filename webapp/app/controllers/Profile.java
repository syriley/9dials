package controllers;

import java.util.HashMap;
import java.util.Map;

import models.Instrument;
import models.Picture;
import models.User;
import play.data.validation.Email;
import play.data.validation.Required;
import play.libs.Crypto;
import play.mvc.Router;

public class Profile extends LoggedInController {
	
	public static void index() {
		render();
	}
	
	public static void edit() {
		 render();
	 }
	
	public static void settings() {
		 render();
	}
	
	
	public static void createUsername(@Required String username){
		User user = User.find("byEmail", Security.connected()).first();
		validateUsernameNotTaken(username);
		if(validation.hasErrors()) {
	          params.flash(); // add http parameters to the flash scope
	          validation.keep(); // keep the errors for the next request
	          NewUser.index();
	    }
		user.username=username;
		user.save();
		NewUser.index();
	}

	private static void validateUsernameNotTaken(String username) {
		User userCheck = User.find("byUsername", username).first();
		if(userCheck!=null){
			validation.addError("username", "Username is already taken, please choose another");
		}
	}
	
	public static void updateBasics(@Required String name, @Required String username,@Email String email){		
		User user = User.find("byEmail", Security.connected()).first();
		validateUsernameNotTaken(username);
		if(validation.hasErrors()) {
	          params.flash(); // add http parameters to the flash scope
	          validation.keep(); // keep the errors for the next request
	          settings();
	    }
		user.name=name;
		user.username=username;
		user.email=user.email;
		user.save();
        settings();
	}
	
	public static void createPassword(@Required String newpw, @Required String newpw2){		
		User user = User.find("byEmail", Security.connected()).first();
		validation.equals("newpw", newpw,"confirmed password", newpw2);
		user.password=Crypto.passwordHash(newpw);
		if(validation.hasErrors()) {
	          params.flash(); // add http parameters to the flash scope
	          validation.keep(); // keep the errors for the next request
	          settings();
	    }		
		user.save();
        settings();
	}
	public static void updatePassword(@Required String oldpw,@Required String newpw, @Required String newpw2){		
		User user = User.find("byEmail", Security.connected()).first();		
		changePassword(user,oldpw,newpw,newpw2);
		if(validation.hasErrors()) {
	          params.flash(); // add http parameters to the flash scope
	          validation.keep(); // keep the errors for the next request
	          settings();
	    }
		user.save();
        settings();
	}

	private static void changePassword(User user,String oldpw, String newpw, String newpw2) {
		if(oldpw!=null&&newpw!=null&&newpw2!=null){
			validation.equals("newpw", newpw,"confirmed password", newpw2);
			validation.equals("oldpw", Crypto.passwordHash(oldpw),"your existing password",user.password);
		}		
	}

	public static void addInstrument (String name) {		
		User user = User.find("byEmail", Security.connected()).first();
		Instrument instrument = Instrument.find("byName", name).first();
		if (instrument == null) {
			instrument = new Instrument(name);
		}
		user.addInstrument(instrument);
		user.save();
        edit();        
	}
	
	public static void uploadPicture(Picture picture) {
        picture.save();
        User user = User.find("byEmail", Security.connected()).first();
		String url = getPictureUrl(picture);
		user.setImageUrl(url);
		user.save();
        edit();
    }

	private static String getPictureUrl(Picture picture) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", picture.id);			
		String url = Router.reverse("PictureServer.getPicture", map).url;
		return url;
	}	
}
