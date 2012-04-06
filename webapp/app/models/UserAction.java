package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class UserAction extends Model {
    
    @OneToOne
    public User user;
    public String action;
    public Date actionTime;
    
    public UserAction(User user, String action) {
        super();
        this.user = user;
        this.action = action;
        actionTime = new Date();
    }
    
    
}
