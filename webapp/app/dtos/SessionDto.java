package dtos;

import java.util.Date;

import javax.persistence.Column;

public class SessionDto {

    public Date modified;
    public String name;
    public String description;
    public String access;
    public String data;
    public boolean enabled = true;
    
    public SessionDto(Date modified, String name, String description,
            String access, String data, boolean enabled) {
        super();
        this.modified = modified;
        this.name = name;
        this.description = description;
        this.access = access;
        this.data = data;
        this.enabled = enabled;
    }
}
