package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
import util.JsonUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Entity
public class Session extends Model{
	
	public Date modified;
	public String name;
	public String description;
	public String access;
	@Column(length=2048)
	public String data;
	public boolean enabled = true;
	
	@OneToMany(mappedBy="session", cascade=CascadeType.ALL)
	public volatile List<UserSession> userSessions;
	
	
	public Session() {
		userSessions = new ArrayList<UserSession>();
	}
	
	public void shareWithUser(User user) {
		new UserSession(user, this, "collaborator").save();
	}
	
	public void removeUser(Long userId) {
		UserSession userSession = UserSession.findByUserAndSession(userId, this.id);
		userSession.delete();
	}
	
	public static Session findByName(String name) {
	    return Session.find("byName", name).first();
	}
	
	public List<UserSession> getUserSessions() {
		return UserSession.getSharedUserSessions(this);
	}
	
	public int getTrackIdFromRegionId(int id) {
	    JsonObject session = JsonUtils.getJsonObject(data);
	    JsonArray tracks = session.get("tracks").getAsJsonArray();
	    for (JsonElement track : tracks) {
	        JsonArray regions = track.getAsJsonObject().get("regions").getAsJsonArray();
	        if(JsonUtils.getById(regions, id) != null){
	            return track.getAsJsonObject().get("id").getAsInt();
	        }
        }
	    return -1;
	}
}
