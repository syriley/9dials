package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Source extends Model
{
    
    public Source(User creator,String fileName, String s3key, String sourceName, String url) {
        super();
        this.creator=creator;
        this.fileName = fileName;
        this.s3key = s3key;
        this.name=sourceName;
        this.url=url;
    }
    
    public String fileName;
    public String name;
    public String s3key;
    public String url;
    public long playCount;
    
    @Column(updatable=false, insertable=false)
    public Date createDate;
    
    @ManyToOne
    public User creator;
    
    public Source registerPlay(){
    	playCount++;
    	return this;
    }
}
