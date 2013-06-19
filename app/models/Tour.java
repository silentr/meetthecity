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
import javax.persistence.OneToOne;

import com.avaje.ebean.Ebean;

import play.data.format.Formats;
import play.db.ebean.Model;

@Entity
public class Tour extends Model {

    private static final long serialVersionUID = 1L;

    public static Finder<Long, Tour> find = new Finder<Long, Tour>(Long.class, Tour.class);

    @Id
    public long id;

    public String name;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    public Date date;

    public double price;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    public User guide;

    @Column(columnDefinition = "TEXT")
    public String descriptionFull;

    @Column(columnDefinition = "TEXT")
    public String descriptionShort;

    public String descriptionMini;

    public String photoName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "tour")
    public List<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tour_to_tourist", joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    public List<User> tourists;

    public String getDateFormatted() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new Locale("de"));
        return df.format(date);
    }

    public Boolean join(User user) {
          try{
              Ebean.beginTransaction();
              tourists.add(user);
              user.tours.add(this);
              update();
              user.update();
              Ebean.commitTransaction();
              return true;
          }finally{
              Ebean.endTransaction();
          }
    }
    
    public Boolean leave(User user) {
        try{
            Ebean.beginTransaction();
            tourists.remove(user);
            user.tours.remove(this);
            update();
            user.update();
            Ebean.commitTransaction();
            return true;
        }finally{
            Ebean.endTransaction();
        }
  }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((descriptionFull == null) ? 0 : descriptionFull.hashCode());
        result = prime * result + ((descriptionMini == null) ? 0 : descriptionMini.hashCode());
        result = prime * result + ((descriptionShort == null) ? 0 : descriptionShort.hashCode());
        result = prime * result + ((guide == null) ? 0 : guide.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((photoName == null) ? 0 : photoName.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((reviews == null) ? 0 : reviews.hashCode());
        result = prime * result + ((tourists == null) ? 0 : tourists.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Tour other = (Tour) obj;
        if (date == null) {
            if (other.date != null) return false;
        } else if (!date.equals(other.date)) return false;
        if (descriptionFull == null) {
            if (other.descriptionFull != null) return false;
        } else if (!descriptionFull.equals(other.descriptionFull)) return false;
        if (descriptionMini == null) {
            if (other.descriptionMini != null) return false;
        } else if (!descriptionMini.equals(other.descriptionMini)) return false;
        if (descriptionShort == null) {
            if (other.descriptionShort != null) return false;
        } else if (!descriptionShort.equals(other.descriptionShort)) return false;
        if (guide == null) {
            if (other.guide != null) return false;
        } else if (!guide.equals(other.guide)) return false;
        if (id != other.id) return false;
        if (location == null) {
            if (other.location != null) return false;
        } else if (!location.equals(other.location)) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (photoName == null) {
            if (other.photoName != null) return false;
        } else if (!photoName.equals(other.photoName)) return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price)) return false;
        if (reviews == null) {
            if (other.reviews != null) return false;
        } else if (!reviews.equals(other.reviews)) return false;
        if (tourists == null) {
            if (other.tourists != null) return false;
        } else if (!tourists.equals(other.tourists)) return false;
        return true;
    }
    
    

}
