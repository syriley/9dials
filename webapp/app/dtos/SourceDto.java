package dtos;

import java.util.Date;

import javax.persistence.Column;

public class SourceDto {
    
    public long id;
    public String fileName;
    public String name;
    public String s3key;
    public String url;
    public long playCount;
    public Date createDate;
    
    public SourceDto(long id, String fileName, String name, String s3key, String url,
            long playCount, Date createDate) {
        super();
        this.id = id; 
        this.fileName = fileName;
        this.name = name;
        this.s3key = s3key;
        this.url = url;
        this.playCount = playCount;
        this.createDate = createDate;
    }
    
    
}
