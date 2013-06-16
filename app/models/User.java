package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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

    public String photo;

    public String firstname;

    public String lastname;

    public int rating;

    public String country;

    @OneToMany(mappedBy = "tourist", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Review> reviewsGiven;

    @OneToMany(mappedBy = "guide", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Review> reviewsReceived;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tourists")
    public List<Tour> tours;

    public static User authenticate(String username, String password) {
        return find.where().eq("username", username).eq("password", password).findUnique();
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", email=" + email + ", phone=" + phone
                + ", photo=" + photo + ", firstname=" + firstname + ", lastname=" + lastname + ", rating=" + rating
                + ", country=" + country + ", reviewsGiven=" + reviewsGiven + ", reviewsReceived=" + reviewsReceived
                + ", tours=" + tours + "]";
    }

}
