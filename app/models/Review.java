package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model.Finder;

@Entity
public class Review {
	private static final long serialVersionUID = -559026603187724583L;

    public static Finder<String, Review> find = new Finder<String, Review>(String.class, Review.class);
    
    @Id
    private Long id;
    private String comment;
    private int rating;
    private User user;
    
    
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
    
    
}
