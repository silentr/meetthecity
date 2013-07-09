package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonBackReference;

import play.db.ebean.Model;

@Entity
public class Review extends Model {

    private static final long serialVersionUID = 55900318772583L;

    public static Finder<Long, Review> find = new Finder<Long, Review>(Long.class, Review.class);

    @Id
    public long id;
    public String comment;
    public int rating;

    @JsonBackReference
    @ManyToOne
    public User guide;

    @JsonBackReference
    @ManyToOne
    public User tourist;

    @JsonBackReference
    @ManyToOne
    public Tour tour;
    
    public static List<Review> reviews(User tourist, User guide){
        return find.where().eq("tourist", tourist).eq("guide", guide).findList();
    }
    
    public static List<Review> reviews(User guide){
        return find.where().eq("guide", guide).findList();
    }

    @Override
    public String toString() {
        return "Review [id=" + id + ", comment=" + comment + ", rating=" + rating
                + ", tourist=" + tourist.username + "]";
    }
}
