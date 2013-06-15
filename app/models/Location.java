package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Location extends Model {
	private static final long serialVersionUID = -5592660318772583L;

	public static Finder<String, Location> find = new Finder<String, Location>(
			String.class, Location.class);
	@Id
	private int id;
	private int length;
	private int width;
	private String name;
	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Tour> tours;

	public Location() {
	}

	public Location(int length, int width, String name) {
		super();
		this.length = length;
		this.width = width;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", length=" + length + ", width=" + width
				+ ", name=" + name + "]";
	}

}
