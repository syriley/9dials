package controllers;

import java.util.HashMap;
import java.util.Map;

import models.Instrument;
import models.Picture;
import models.User;
import play.Logger;
import play.mvc.Router;

public class Profile extends LoggedInController {
	
	public static void index() {
		render();
	}
	
	public static void edit() {
		 render();
	 }
		
	public static void updateBasics(String name){
		User user = User.find("byEmail", Security.connected()).first();
		user.name=name;
		user.save();
        edit();
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
