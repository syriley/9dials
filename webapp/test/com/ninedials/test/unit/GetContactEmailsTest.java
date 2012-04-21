package com.ninedials.test.unit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import jobs.GetContactEmails;

import models.School;

import org.junit.Before;
import org.junit.Test;

import controllers.Instruments;

public class GetContactEmailsTest extends NineDialsTest {

    private GetContactEmails getContactEmails;
    private School school;
    @Before
    public void setup() {
        super.setup();
        getContactEmails = new GetContactEmails();
    }
    
    private void setSchool(String website) {
        school = new School();
        school.website = website;
    }
//    @Test
//    public void mailToTest() {
//        setSchool("http://www.twochordsmore.co.uk/");
//        getContactEmails.extractContactDetails(school);
//        
//        assertEquals("Actual size " + school.possibleEmails.size(), 1, school.possibleEmails.size());
//        assertEquals("Name " + school.email, "andy.anderson@twochordsmore.co.uk", school.email);
//    }

    //@Test doesn't work
    public void myInternationalTest() {
        setSchool("http://mwtinternationalmusicschool.co.uk");
        getContactEmails.extractContactDetails(school);
        
        assertEquals("Actual size " + school.possibleEmails.size(), 1, school.possibleEmails.size());
        assertEquals("Name " + school.email, "intune.info@btinternet.com", school.email);
    }
    
//    @Test
//    public void aylesburyTest() {
//        setSchool("http://www.aylesburypianotuition.co.uk/");
//        getContactEmails.extractContactDetails(school);
//        
//        assertEquals("Actual size " + school.possibleEmails.size(), 2, school.possibleEmails.size());
//        assertEquals("Name " + school.email, null, school.email);
//    }
//    
//    @Test
//    public void twobTest() {
//        setSchool("http://www.2b-intune.com/");
//        getContactEmails.extractContactDetails(school);
//        
//        assertEquals("Actual size " + school.possibleEmails.size(), 1, school.possibleEmails.size());
//        assertEquals("Name " + school.email, "intune.info@btinternet.com", school.email);
//    }
//    
//    @Test
//    public void twitterAndFacebookTest() {
//        setSchool("http://www.fidlermusic.co.uk/");
//        getContactEmails.extractContactDetails(school);
//        assertEquals("Actual size " + school.possibleEmails.size(), 0, school.possibleEmails.size());
//        assertEquals("Name " + school.twitter, "http://twitter.com/fidlermusic", school.twitter);
//        assertEquals("Name " + school.facebook, "http://www.facebook.com/#!/pages/Fidler-Music/182287471792845", school.facebook);
//    }
//    
//    @Test
//    public void StringbrokersTest() {
//        setSchool("http://www.stringbrokers.com/");
//        getContactEmails.extractContactDetails(school);
//        assertEquals("Actual size " + school.possibleEmails.size(), 1, school.possibleEmails.size());
//        assertEquals("Name " + school.twitter, "http://www.twitter.com/Stringbrokers", school.twitter);
//        assertEquals("Name " + school.facebook, "http://www.facebook.com/pages/Stringbrokers-Music-Ltd/157849170958645", school.facebook);
//    }
}
