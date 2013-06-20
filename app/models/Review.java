package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonBackReference;

import play.db.ebean.Model.Finder;

@Entity
public class Review {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -55902660318772583L;

    public static Finder<Long, Review> find = new Finder<Long, Review>(Long.class, Review.class);

    @Id
    public Long id;
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

    @Override
    public String toString() {
        return "Review [id=" + id + ", comment=" + comment + ", rating=" + rating + ", guide=" + guide.username
                + ", tourist=" + tourist.username + ", tour=" + tour.name + "]";
    }

}
