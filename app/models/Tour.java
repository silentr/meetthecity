package models;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.format.Formats;
import play.db.ebean.Model;

@Entity
public class Tour extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Finder<Long,Tour> find = new Finder<Long,Tour>(Long.class, Tour.class);
	
	@Id
	private Long id;
	private String name;
	@Formats.DateTime(pattern="yyyy-MM-dd")
	private Date date;
	private Double price;
	@ManyToOne(fetch=FetchType.EAGER)
	private Location location;
	@ManyToOne(fetch=FetchType.EAGER)
	private User guide;
	@Column(columnDefinition="TEXT")
	private String descriptionFull;
	@Column(columnDefinition="TEXT")
	private String descriptionShort;
	private String descriptionMini;
	private String photoName;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="tour")
	private List<Review> reviews;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="tour_to_tourist",
	        joinColumns=
	        @JoinColumn(name="id", referencedColumnName="id"),
	        inverseJoinColumns=
	        @JoinColumn(name="username", referencedColumnName="username")
	        )
	private List<User> tourists;
	
	public Tour(){}

	public Tour(String name, Date date, Double price,
			Location location, User guide, String descriptionFull,
			String descriptionShort, String descriptionMini, String photoName,
			List<Review> reviews) {
		this.name = name;
		this.date = date;
		this.price = price;
		this.location = location;
		this.guide = guide;
		this.descriptionFull = descriptionFull;
		this.descriptionShort = descriptionShort;
		this.descriptionMini = descriptionMini;
		this.photoName = photoName;
		this.reviews = reviews;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDateFormatted(){
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new Locale("de"));
		return df.format(date);
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getGuide() {
		return guide;
	}

	public void setGuide(User guide) {
		this.guide = guide;
	}

	public String getDescriptionFull() {
		return descriptionFull;
	}

	public void setDescriptionFull(String descriptionFull) {
		this.descriptionFull = descriptionFull;
	}

	public String getDescriptionShort() {
		return descriptionShort;
	}

	public void setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
	}

	public String getDescriptionMini() {
		return descriptionMini;
	}

	public void setDescriptionMini(String descriptionMini) {
		this.descriptionMini = descriptionMini;
	}
	
	public void join(User user){
		
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<User> getTourists() {
        return tourists;
    }

    public void setTourists(List<User> tourists) {
        this.tourists = tourists;
    }

    @Override
	public String toString() {
		return "Tour [id=" + id + ", name=" + name + ", date=" + date
				+ ", price=" + price + ", location=" + location + ", guide="
				+ guide + ", descriptionFull=" + descriptionFull
				+ ", descriptionShort=" + descriptionShort
				+ ", descriptionMini=" + descriptionMini + ", photoName="
				+ photoName + ", reviews=" + reviews + "]";
	}

}
