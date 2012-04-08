package securesocial.provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import models.User;
import play.Logger;
import play.libs.Codec;
import play.libs.Crypto;
import play.mvc.Scope.Flash;
import securesocial.provider.SocialUser;
import securesocial.provider.UserId;
import securesocial.provider.UserService;
/**
 * The default user service provided with SecureSocial.
 * If users need to find/save users in a custom backing store they only
 * need to provide an implementation of the UserService.Service interface in their app. It will be picked up automatically.
 *
 * This class it not suitable for a production environment.  It is only meant to be used in development.  For production use
 * you need to provide your own implementation.
 *
 * @see UserService.Service
 * @see securesocial.plugin.SecureSocialPlugin
 */
public class BasicUserService implements UserService.Service {

    private Map<String, SocialUser> users = Collections.synchronizedMap(new HashMap<String, SocialUser>());
    private Map<String, SocialUser> activations = Collections.synchronizedMap(new HashMap<String, SocialUser>());

    public SocialUser find(UserId id) {
    	SocialUser user = users.get(id.id + id.provider.toString());    	
    	if(id.provider.name().equals("userpass")){
    		user = getStoredUserPassUser(id);    	
    	}
    	if(user!=null){
    		User ouruser = User.find("byEmail", user.email).first();
    		if(ouruser == null){
    			Logger.info("Creating user for: "+user.email);
				ouruser  = new User();
				ouruser.email=user.email;
				ouruser.name=user.displayName;
				ouruser.username=user.id.id;
				ouruser.save();
				play.mvc.Scope.Flash.current().put("newuser",true);
    		}
    		play.mvc.Scope.Session.current().put("username", user.email);
    	}
        return user;
    }

	private SocialUser getStoredUserPassUser(UserId id) {
		User ouruser = User.find("byEmail", id.id).first();
		SocialUser user = null;
		if(ouruser!=null){
			user = new SocialUser();
			user.password=ouruser.password;
			user.id=id;
			user.email=ouruser.email;
			user.isEmailVerified=ouruser.isEmailVerified;
		}
		return user;
	}

	private SocialUser fakeLogin(UserId id) {
		SocialUser user = new SocialUser();
		
		user.email=id.id;
		user.isEmailVerified=true;
		user.password=Crypto.passwordHash("password");
		user.id=id;
		return user;
	}

    public void save(SocialUser user) {
    	user.isEmailVerified=true;
		User ouruser = User.find("byEmail", user.email).first();
		if(ouruser == null){
			Logger.info("Creating user for: "+user.email);
			ouruser  = new User();
			ouruser.email=user.email;	
			ouruser.name=user.displayName;
			ouruser.password=user.password;
			ouruser.save();
			Flash.current().put("newuser", "true");		
		}
		play.mvc.Scope.Session.current().put("username", user.email);
        users.put(user.id.id + user.id.provider.toString(), user);
    }

    public String createActivation(SocialUser user) {
        final String uuid = Codec.UUID();
        activations.put(uuid, user);
        return uuid;
    }

    public boolean activate(String uuid) {
        SocialUser user = activations.get(uuid);
        boolean result = false;

        if( user != null ) {
            user.isEmailVerified =  true;
            save(user);
            activations.remove(uuid);
            result = true;
        }
        return result;
    }

    public void deletePendingActivations() {
        activations.clear();
    }
}
