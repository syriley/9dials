package dtos;

public class UserDto {

    public String name;
    public String username;
    public String email;
    public String password;
    public String bio;
    public String imageUrl;
    public String externalId;
    public String externalProvider;
    public boolean isEmailVerified = true;
    
    public UserDto(String name, String username, String email, String password,
            String bio, String imageUrl, String externalId,
            String externalProvider, boolean isEmailVerified) {
        super();
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.externalId = externalId;
        this.externalProvider = externalProvider;
        this.isEmailVerified = isEmailVerified;
    }
    
    
}
