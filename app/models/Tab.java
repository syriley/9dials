//package models;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Pattern;
//
//import org.apache.commons.lang.StringUtils;
//
//import play.modules.morphia.Model;
//
//import com.google.code.morphia.Datastore;
//import com.google.code.morphia.annotations.Entity;
//import com.google.code.morphia.query.Criteria;
//import com.google.code.morphia.query.Query;
//import com.google.code.morphia.query.UpdateOperations;
//
//@Entity
//public class Tab extends Model {
//        public String url;
//        public String artist;
//        public String title;
//        public int rating;
//        public String type;
//        public int numberOfVotes;
//        public String idString;
//        public Date updated;
//        public Date pageProcessed;
//        public Date dateTabStarted;
//        public Date dateTabCompleted;
//        public String originalTabText;
//        public boolean expire;
//        public List<String> videoLinks;
//        
//        public URL getUrl() {
//            try {
//                    return new URL(url);
//            } catch (MalformedURLException e) {
//                    e.printStackTrace();
//            }
//            return null;
//         }
//
//         public void setUrl(URL url) {
//                 this.url = url.toExternalForm();
//         } 
//         
//         public static List<Tab> list (String search) {
//                 MorphiaQuery query = Tab.q();
//                 if (StringUtils.isNotEmpty(search)) {
//                         query.filter("artist", Pattern.compile(search, Pattern.CASE_INSENSITIVE));
//                 }
//                 
//                 query.criteria("originalTabText").exists();
//                 List<Tab> tabs = query.limit(200).asList();
//                 return tabs;
//         }
//         
//         public static Tab getOne(String tabType, String crawlType) {
//                 Tab tab = null;
//                 if(crawlType == null || crawlType.equals("tab")) {
//                         Datastore datastore = Tab.ds();
//                         MorphiaQuery morphiaQuery = Tab.q();
//                         morphiaQuery.filter("type", tabType);
//                         morphiaQuery.filter("dateTabStarted", null);
//                         Query query = morphiaQuery.limit(1).getMorphiaQuery();
//                         UpdateOperations<Tab> updateOperations = datastore.createUpdateOperations(Tab.class).set("dateTabStarted", new Date());
//                         tab = datastore.findAndModify(query, updateOperations);
//                 }
//                 return tab;
//         }
//     }
