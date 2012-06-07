package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jobs.AudioConverterJob;

import models.Source;
import models.User;

import org.apache.commons.io.IOUtils;

import play.Logger;
import play.libs.F.Promise;
import play.modules.facebook.FbGraphException;
import play.mvc.Router;
import util.FileUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class TheSources extends LoggedInController{
	  
	public static final String AWS_ACCESS_KEY = "AKIAJLGV564C5LYG5WHQ";
	public static final String AWS_SECRET_KEY = "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";
    public static final String BUCKET_NAME = "media.9dials.com";
  
    public static String getFileUrl(String fileName){
    	return "http://"+BUCKET_NAME+"/"+fileName;
    }
    
	public static void index() {
		render();
	}

	public static void audio() {
		render();
	}
	;
    String s3key = UUID.randomUUID().toString();
   
	public static void upload(File file, String name) throws FileNotFoundException  {
			
	    String extension = FileUtils.extension(file);
	    String s3key = UUID.randomUUID().toString();
	    Source source = null;
	    if(extension.equals("ogg")){
            String mp3Name = s3key+".mp3";
            Promise<File> mp3 = new AudioConverterJob(file, s3key, "mp3").now();
            File mp3File = await(mp3);
            source = uploadToS3(mp3File,mp3Name,name);
        }
        else{
            source = uploadToS3(file,s3key,name);
        }
	    renderJSON(source);
	}
	
   private static Source uploadToS3(File file, String objectKey,String name)   {
        User user = null;  
        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AmazonS3 s3Client = new AmazonS3Client(awsCredentials);
        s3Client.putObject(BUCKET_NAME, objectKey, file);
        String url = getLocalUrl(objectKey);
        Source source = new Source(user,file.getName(),objectKey,name, url);
        source.save();    
        return source;
    }
	   
	private static String getLocalUrl(String s3key) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sourceId", s3key);	
		String url = Router.getFullUrl("MyClips.index", map);
		return url;
	}

	private static String getPlayableUrl(String s3key) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sourceId", s3key);	
		String url = Router.getFullUrl("Share.index", map);
		return url;
	}
	
	public static void uploadLocally(File file) {
	    
	    FileOutputStream moveTo = null;
	    try {
	        moveTo = new FileOutputStream(new File("/tmp/test.ogg"));
	        FileInputStream fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, moveTo);
        } catch (IOException e) {
            Logger.error(e, "local upload error");
        }
	}
}
