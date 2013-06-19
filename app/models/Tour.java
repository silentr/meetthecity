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
    @JoinTable(name = "tour_to_tourist", joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
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
    public String toString() {
        return "";
    }

}
