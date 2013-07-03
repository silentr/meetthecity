import java.util.List;
import java.util.Map;

import models.Review;
import models.Tour;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void onStart(Application app) {
        Logger.info("Application has started");

        Map yamlMap = (Map) Yaml.load("initial-data.yml");
        List<User> users = (List<User>) yamlMap.get("users");
        List<Tour> tours = (List<Tour>) yamlMap.get("tours");
        List<Review> reviews = (List<Review>) yamlMap.get("reviews");

        // Check if the database is empty
        if (User.find.findRowCount() == 0) {
            Ebean.save(users);
        }
        
        if (Tour.find.findRowCount() == 0) {
            Ebean.save(tours);
        }
        
        if (Review.find.findRowCount() == 0) {
            Ebean.save(reviews);
        }
    }
}
