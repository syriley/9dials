import java.util.List;

import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.Crypto;
import play.test.Fixtures;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
        // Check if the database is empty
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
