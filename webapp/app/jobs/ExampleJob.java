package jobs;
 
import play.Logger;
import play.jobs.*;
 
public class ExampleJob extends Job {
    
    public void doJob() {
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
    }
    
}