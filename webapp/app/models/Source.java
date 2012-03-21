package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Source extends Model
{
    
    public Source(String fileName, String s3Key) {
        super();
        this.fileName = fileName;
        this.s3Key = s3Key;
    }
    
    public String fileName;
    public String s3Key;
}
