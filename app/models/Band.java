package models;

//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Pattern;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import play.modules.morphia.Model;
//
//import com.google.code.morphia.Datastore;
//import com.google.code.morphia.annotations.Entity;
//import com.google.code.morphia.query.Query;
//import com.google.code.morphia.query.UpdateOperations;
//
//@Entity 
public class Band {//extends Model {
        
//        Logger logger = LoggerFactory.getLogger(Band.class);
//        private static final long serialVersionUID = 6397424855980948476L;
//        public String idString;
//        public String name;
//        public Date dateCompleted;
//        public Date dateStarted;
//        public String url;      
//        public int numberOfTabs;
//        
//        public URL getUrl() {
//                try {
//                        return new URL (url);
//                } catch (MalformedURLException e) {
//                        logger.error(e.toString());
//                }
//                return null;
//        }
//        public void setUrl(URL url) {
//                this.url = url.toString();
//        }
//        public static Band getNext() {
//            Query query = Band.q()
//                            .filter("dateCompleted = ", null)       
//                            .filter("dateStarted != ", null)
//                            .order("-numberOfTabs")
//                            .limit(1).getMorphiaQuery();
//            Datastore datastore = Band.ds();
//            UpdateOperations<Band> updateOperations = datastore.createUpdateOperations(Band.class).set("dateStarted", new Date());
//            Band band = datastore.findAndModify(query,updateOperations);
//            return band;
//    }
//    
//    public static List<Band> list(String search) {
//            
//            MorphiaQuery query = Band.q();
//            if (StringUtils.isNotEmpty(search)) {
//                    query.filter("name", Pattern.compile(search, Pattern.CASE_INSENSITIVE));
//            }
//            return query.limit(20).asList();
//    }
//    public static List<Band> search(String searchTerm) {
//            List<Band> bands = Band.q().filter("name",
//                            Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE)).asList();
//            
//            return bands;
//    }
}