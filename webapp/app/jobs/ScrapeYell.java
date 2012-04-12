package jobs;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Address;
import models.Location;
import models.School;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import play.Logger;
import play.db.jpa.JPA;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import util.connection.ClientFactory;
import util.connection.HttpClient;
 
@OnApplicationStart(async=true)
public class ScrapeYell extends Job {
    //TODO:add yelp and touchlocal
    private static final String YELL_SEARCH_URL = 
        "http://www.yell.com/ucs/UcsSearchAction.do?location=?YYY&keywords=XXX";
    
    private static final List<String> KEYWORDS = new ArrayList<String>();
    
    static {
        KEYWORDS.add("guitar+lessons");
        KEYWORDS.add("music+lessons");
    }
    public void doJob() {
        Logger.debug("Starting Yell Scraper");
        HttpClient client = ClientFactory.getInstance().getClient("anonymous");
        List<Location> locations = Location.findUnprocessed();
        for (Location location : locations) {
            for (String keyword : KEYWORDS) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                String locationString = location.city.replaceAll(" ", "+");
                locationString = locationString.replaceAll("&", "");
                Logger.info("Getting Yell info for %s in %s", keyword, locationString);   
                try {
                    URL url = new URL(YELL_SEARCH_URL.replace("XXX", keyword).replaceAll("YYY", locationString));
                    Document document = client.downloadAsDocument(url);
                    Logger.debug("Response = %s ", document);
                    Elements schools = document.select("div.parentListing");
                    
                    Logger.debug("Number of schools is %d", schools.size());
                    
                    for (Element schoolElement : schools) {
                        Logger.debug("School response = \n %s", schoolElement.toString());
                        School school = new School();
                        Element name = schoolElement.select("span.fn").first();
                        Element phoneNumber = schoolElement.select("span.tel").first();
                        Element streetAddress = schoolElement.select("span.street-address").first();
                        Element locality = schoolElement.select("span.locality").first();
                        Element postalCode = schoolElement.select("span.postal-code").first();
                        Element website = schoolElement.select("a.url").first();
                        Elements photos = schoolElement.select("img.photo");
                        
                        school.name = name.text();
                        if(phoneNumber != null) {
                            school.phoneNumbers.add(phoneNumber.text());
                        }
                        
                        if(website != null) {
                            school.website = website.attr("href");
                        }
                        
                        Logger.debug("shool %s has website %s", school.name, school.website);
                        Address address = school.address;
                        
                        if(streetAddress != null) {
                            address.streetAddress = streetAddress.text();
                        }
                        
                        if(locality != null) {
                            address.locality = locality.text();
                        }
                        
                        if(postalCode != null) {
                            address.postalCode = postalCode.text();
                        }
                        
                        for (Element photo : photos) {
                            school.photoUrls.add(photo.attr("src"));
                        }
                        
                        if(!JPA.em().getTransaction().isActive()) {
                            JPA.em().getTransaction().begin();                            
                        }
                        //handle unique key in java as JPA transactions rank
                        if(school.website != null && School.findByWebsite(school.website) == null) {
                            Logger.debug("School has a website so saving");
                            address.save();
                            school.save();
                        }
                    }
                    
                    if(JPA.em().getTransaction().isActive()) {
                        JPA.em().flush();
                        JPA.em().getTransaction().commit();
                    }
                    
                    JPA.em().getTransaction().begin();
                    Logger.info("Updating location");
                    location.dateProcessed = new Date();
                    location.save();
                    JPA.em().flush();
                    JPA.em().getTransaction().commit();
                    
                    Thread.sleep(RandomUtils.nextInt(20) * 1000);
                } 
                catch (ClientProtocolException e) {
                    Logger.error("Could not retrieve Yell page %s", e);
                } 
                catch (IOException e) {
                    Logger.error("Could not retrieve Yell page %s", e);
                } 
                catch (InterruptedException e) {
                    Logger.error("Sleep interrupted %s", e);
                }
               
                catch (Exception e) {
                    Logger.error("Unexpected Exception, %s ", e.getMessage());
                    Logger.error("Stack Trace, %s ", e.getStackTrace());
                    
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
