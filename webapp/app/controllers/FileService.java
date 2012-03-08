package controllers;

import java.io.File;

import models.Track;
import play.Logger;
import play.libs.Files;

public class FileService extends LoggedInController{
	public static void index() {
		render();
	}
	 public static void uploadTrack(File audio) {
		 File destination = new File("/tmp/"+audio.getName());
		 Files.copy(audio, destination);
		 Logger.info(destination.getAbsolutePath());
		 Track uploaded = new Track();		 
		 uploaded.audioUrl=destination.getAbsolutePath();
		 uploaded.provider="FILE";		 
		 uploaded.save();
		 FileService.index();
	 }
	 
	 public static void getTrack(long id ){
		 Track track = Track.findById(id);
		 response.setContentTypeIfNotSet("audio/ogg");
	     renderBinary(new File(track.audioUrl));
	 }
}
