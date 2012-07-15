package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.commons.io.IOUtils;

import play.Logger;
import play.Play;
import play.db.jpa.Model;
import play.modules.facebook.FbGraphException;
import play.mvc.Router;
import play.mvc.Router.ActionDefinition;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import dtos.SourceDto;

@Entity
public class Source extends Model
{
    public static final String AWS_ACCESS_KEY = "AKIAJLGV564C5LYG5WHQ";
    public static final String AWS_SECRET_KEY = "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";
    public static final String BUCKET_NAME = "media.9dials.com";
    
    public static String getFileUrl(String fileName){
        return "http://"+BUCKET_NAME+"/"+fileName;
    }
  
    public Source(User creator,String fileName, String s3key, String sourceName, String url) {
        super();
        this.creator=creator;
        this.fileName = fileName;
        this.s3key = s3key;
        this.name=sourceName;
        this.url=url;
    }
    
    public String fileName;
    public String name;
    public String s3key;
    public String url;
    public long playCount;
    
    @Column(updatable=false, insertable=false)
    public Date createDate;
    
    @ManyToOne
    public User creator;
    
    public Source registerPlay(){
    	playCount++;
    	return this;
    }
    
    public static Source uploadToS3(User user, File file, String objectKey,String name) throws FbGraphException {
        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AmazonS3 s3Client = new AmazonS3Client(awsCredentials);
        s3Client.putObject(BUCKET_NAME, objectKey, file);
        String url = getLocalUrl(objectKey);
        Source source = new Source(user, file.getName(),objectKey,name, url);
        source.save();
        return source;
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
    
    public static String getFull9DialsUrl(String action, Map<String, Object> args) {
        ActionDefinition actionDefinition = Router.reverse(action, args);
        String base = getBase9DialsUrl();
        if (actionDefinition.method.equals("WS")) {
            return base.replaceFirst("https?", "ws") + actionDefinition;
        }
        return base + actionDefinition;
    }
    
    public static String getFullUrl(String action, Map<String, Object> args) {
        ActionDefinition actionDefinition = Router.reverse(action, args);
        String base = getBaseUrl();
        if (actionDefinition.method.equals("WS")) {
            return base.replaceFirst("https?", "ws") + actionDefinition;
        }
        return base + actionDefinition;
    }
    
    private static String getLocalUrl(String s3key) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("sourceId", s3key); 
        String url = getFull9DialsUrl("MyClips.index", map);
        return url;
    }

    private static String getPlayableUrl(String s3key) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("sourceId", s3key); 
        String url = getFull9DialsUrl("Share.index", map);
        return url;
    }

    // Gets baseUrl from current request or application.baseUrl in application.conf
    protected static String getBase9DialsUrl() {
        // No current request is present - must get baseUrl from config
        String appBaseUrl = Play.configuration.getProperty("application.baseUrl", "application.baseUrl");
        if (appBaseUrl.endsWith("/")) {
            // remove the trailing slash
            appBaseUrl = appBaseUrl.substring(0, appBaseUrl.length()-1);
        }
        return appBaseUrl;
    }

    protected static String getBaseUrl() {
        // No current request is present - must get baseUrl from config
        String appBaseUrl = Play.configuration.getProperty("facebook.baseUrl", "facebook.baseUrl");
        if (appBaseUrl.endsWith("/")) {
            // remove the trailing slash
            appBaseUrl = appBaseUrl.substring(0, appBaseUrl.length()-1);
        }
        return appBaseUrl;
    }
    
    public static String getIndexUrl(){
        String base = getBaseUrl();
        return base+"/wall";
    }
    
    public SourceDto getDto(){
        return new SourceDto(fileName, name, s3key, url, playCount, createDate);
    }
    
    public static List<SourceDto> getDtosByUser(User user){
        List<Source> sources = Source.find("byCreator", user).fetch();
        List<SourceDto> sourceDtos= new ArrayList<SourceDto>();
        for (Source source : sources) {
            sourceDtos.add(source.getDto());
        }
        return sourceDtos;
    }
}
