import java.util.List;
import java.util.Map;

import models.Location;
import models.Tour;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        Logger.info("Application has started");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<User> users = (List<User>) ((Map) Yaml.load("initial-data.yml")).get("users");
        Logger.info(users.toString());
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Tour> tours = (List<Tour>) ((Map) Yaml.load("initial-data.yml")).get("tours");
        Logger.info(tours.toString());
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Location> locations = (List<Location>) ((Map) Yaml.load("initial-data.yml")).get("locations");
        Logger.info(locations.toString());

        // Check if the database is empty
        if (User.find.findRowCount() == 0) {
            Ebean.save(users);
        }
        
        if(Tour.find.findRowCount() == 0){
        	Ebean.save(tours);
        }
        
        if(Tour.find.findRowCount() == 0){
        	Ebean.save(locations);
        }
    }
}
