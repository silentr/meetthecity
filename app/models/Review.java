package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model.Finder;

@Entity
public class Review {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -55902660318772583L;

    public static Finder<String, Review> find = new Finder<String, Review>(String.class, Review.class);
    
    @Id
    private Long id;
    private String comment;
    private int rating;
    @ManyToOne
    private User guide;
    @ManyToOne
    private User tourist;
    @ManyToOne
    private Tour tour;
    
    public Review(){}
    
	public Review(Long id, String comment, int rating, User guide, User tourist, Tour tour) {
		super();
		this.id = id;
		this.comment = comment;
		this.rating = rating;
		this.guide = guide;
		this.tourist = tourist;
		this.tour = tour;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public User getGuide() {
		return guide;
	}
	public void setGuide(User guide) {
		this.guide = guide;
	}

	public User getTourist() {
		return tourist;
	}

	public void setTourist(User tourist) {
		this.tourist = tourist;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", comment=" + comment + ", rating="
				+ rating + ", guide=" + guide + ", tourist=" + tourist
				+ ", tour=" + tour + "]";
	}
    
    
}
