package jobs;
import java.util.List;

import models.User;
import play.Logger;
import play.Play;
import play.Play.Mode;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.Crypto;
import play.test.Fixtures;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
        Logger.debug("Starting Bootstrap job");
    	reloadData();
    }

    public void reloadData() {
        //don't ever overwrite live data
    	String jpaDdl = Play.configuration.getProperty("jpa.ddl","none"); 
        if (jpaDdl.equals("none")) {
            return;
        }        
        Logger.info("Reloading database");
		Fixtures.deleteDatabase();
	    Fixtures.loadModels("../test/data.yml");
	    encryptUserPasswords();
    }
    
	public void encryptUserPasswords() {
		List<User> users = User.findAll();
		for(User user : users){
			user.password=Crypto.passwordHash(user.password);
			user.save();
		}
	}
 
}
