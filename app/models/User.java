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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        User other = (User) obj;
        if (country == null) {
            if (other.country != null) return false;
        } else if (!country.equals(other.country)) return false;
        if (email == null) {
            if (other.email != null) return false;
        } else if (!email.equals(other.email)) return false;
        if (firstname == null) {
            if (other.firstname != null) return false;
        } else if (!firstname.equals(other.firstname)) return false;
        if (lastname == null) {
            if (other.lastname != null) return false;
        } else if (!lastname.equals(other.lastname)) return false;
        if (password == null) {
            if (other.password != null) return false;
        } else if (!password.equals(other.password)) return false;
        if (phone == null) {
            if (other.phone != null) return false;
        } else if (!phone.equals(other.phone)) return false;
        if (photo == null) {
            if (other.photo != null) return false;
        } else if (!photo.equals(other.photo)) return false;
        if (rating != other.rating) return false;
        if (reviewsGiven == null) {
            if (other.reviewsGiven != null) return false;
        } else if (!reviewsGiven.equals(other.reviewsGiven)) return false;
        if (reviewsReceived == null) {
            if (other.reviewsReceived != null) return false;
        } else if (!reviewsReceived.equals(other.reviewsReceived)) return false;
        if (tours == null) {
            if (other.tours != null) return false;
        } else if (!tours.equals(other.tours)) return false;
        if (username == null) {
            if (other.username != null) return false;
        } else if (!username.equals(other.username)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "";
    }

}
