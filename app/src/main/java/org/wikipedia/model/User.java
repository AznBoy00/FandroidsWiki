package org.wikipedia.model;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String profilePictureUrl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, String profilePictureUrl) {
        this.username = username;
        this.email = email;
        this.profilePictureUrl=profilePictureUrl;
    }

    public void setProfilePictureUrl(String url){
        this.profilePictureUrl= url;
    }
    public String getProfilePictureUrl(){
        return this.profilePictureUrl;
    }

}
