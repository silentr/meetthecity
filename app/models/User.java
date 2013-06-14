package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class User extends Model {

    private static final long serialVersionUID = -559026603187724583L;

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

    @Id
    public String username;

    public String password;

    public String email;
    
    public String phone;

    public String firstname;

    public String lastname;

    public String country;

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", email=" + email + ", firstname="
                + firstname + ", lastname=" + lastname + ", country=" + country + "]";
    }

    public static User authenticate(String username, String password) {
        return find.where().eq("username", username).eq("password", password).findUnique();
    }
}
