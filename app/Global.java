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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void onStart(Application app) {
        Logger.info("Application has started");

        Map yamlMap = (Map) Yaml.load("initial-data.yml");
        List<User> users = (List<User>) yamlMap.get("users");
        Logger.info(users.toString());

        List<Tour> tours = (List<Tour>) yamlMap.get("tours");
        Logger.info(tours.toString());

        List<Location> locations = (List<Location>) yamlMap.get("locations");
        Logger.info(locations.toString());

        // Check if the database is empty
        if (User.find.findRowCount() == 0) {
            Ebean.save(users);
        }

        if (Tour.find.findRowCount() == 0) {
            Ebean.save(locations);
        }

        if (Tour.find.findRowCount() == 0) {
            Ebean.save(tours);
        }
    }
}
