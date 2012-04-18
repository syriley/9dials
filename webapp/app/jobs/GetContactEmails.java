package jobs;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.School;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import play.Logger;
import play.Play;
import play.Play.Mode;
import play.db.jpa.JPA;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import util.connection.ClientFactory;
import util.connection.HttpClient;
 
@OnApplicationStart(async=true)
public class GetContactEmails extends Job {
    
    private static final String EMAIL_PATTERN = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
    private HttpClient client;
    private Pattern pattern;
    private Matcher matcher;
    
    public GetContactEmails() {
        pattern = Pattern.compile(EMAIL_PATTERN);
        client = ClientFactory.getInstance().getClient();
    }
    
    public void doJob() {
        if(Play.mode == Mode.DEV) {
            return;
        }
        
        while(true) {
            School school = School.getSchoolWithoutEmail();
            if(school == null) {
                break;
            }
            Logger.debug("Getting email addresses for %s", school.website);
            extractContactDetails(school);
            
            if(!JPA.em().getTransaction().isActive()) {
                JPA.em().getTransaction().begin();
            }
            Logger.info("Updating location");
            school.updated = new Date();
            school.save();
            JPA.em().flush();
            JPA.em().getTransaction().commit();
        }
        Logger.info("Finishing contact extraction");
    }
    
    public void extractContactDetails(School school) {
        
        Set<URL> pagesToScrape = new HashSet<URL>();
        Set<String> extractedEmails = new HashSet<String>();
        
        try {
            URL rootUrl = new URL (school.website);
            Document startingPage = client.downloadAsDocument(rootUrl);
            Elements links = startingPage.select("a");
            for (Element element : links) {
                if(element.hasAttr("href")) {
                    
                    String href = element.attr("href");
                    if(href.contains("mailto:")) {
                        extractedEmails.add(href.replace("mailto:", ""));
                    }
                    
                    else if (href.contains("twitter.com/")) {
                        school.twitter = href;
                    }
                    
                    else if (href.contains("facebook.com/")) {
                        school.facebook = href;
                    }
                    
                    else if(!href.contains("http")) {
                        if (href.startsWith("/")) {
                            href = href.substring(1);
                        }
                        pagesToScrape.add(new URL(rootUrl.getProtocol() + "://" + rootUrl.getHost() + "/" + href));
                    }
                    
                    else if(href.contains(rootUrl.getHost())) {
                        pagesToScrape.add(new URL(href));
                    }
                }
            }
            
            for (URL url : pagesToScrape) {
                String response = client.download(url);
                matcher = pattern.matcher(response);
                if(matcher.find()) {
                    extractedEmails.add(matcher.group());
                }
                Thread.sleep(500);
            }
            
            school.possibleEmails.addAll(extractedEmails);
            
            if(school.possibleEmails.size() == 1) {
                school.email =  (String)school.possibleEmails.toArray()[0];
            }
        }
        catch (MalformedURLException e) {
            Logger.error("Could not create url for %s", school.website);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                Logger.error("%s", e.getMessage());
            }
        } catch (ClientProtocolException e) {
            Logger.error("%s", e.getMessage());
        } catch (IOException e) {
            Logger.error("%s", e.getMessage());
        } catch (InterruptedException e) {
            Logger.error("%s", e.getMessage());
        }
    }
}
