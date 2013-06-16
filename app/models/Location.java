package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Location extends Model {

    private static final long serialVersionUID = -5592660318772583L;

    public static Finder<Long, Location> find = new Finder<Long, Location>(Long.class, Location.class);

    @Id
    public long id;

    public int ltd;
    public int lng;
    public String country;
    public String city;

    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Tour tour;

    @Override
    public String toString() {
        return "Location [id=" + id + ", ltd=" + ltd + ", lng=" + lng + ", country=" + country + ", city=" + city
                + ", tour=" + tour + "]";
    }
}
