package jobs;
import java.util.List;

import models.AUser;
import play.Play;
import play.Play.Mode;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.Crypto;
import play.test.Fixtures;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
    	reloadData();
    }

    public void reloadData() {
        //don't ever overwrite live data
        if (Play.mode == Mode.PROD) {
            return;
        }
		Fixtures.deleteDatabase();
	    Fixtures.loadModels("../test/data.yml");
	    encryptUserPasswords();
    }
    
	public void encryptUserPasswords() {
		List<AUser> users = AUser.findAll();
		for(AUser user : users){
			user.password=Crypto.passwordHash(user.password);
			user.save();
		}
	}
 
}
