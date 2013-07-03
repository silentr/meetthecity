package controllers;

import java.util.List;

import models.Tour;
import models.User;
import play.api.templates.Html;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;
import views.html.index;
import views.html.home;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(new Html(new StringBuilder())));
    }
    
    public static Result home() {
        List<Tour> tourList = Tour.find.all();
        return ok(home.render(tourList));
    }

    public static Result getAllUsers() {
        List<User> user = User.find.all();

        return ok(Json.toJson(user));
    }

    public static Result getUser(String id) {
        User user = User.find.byId(id);
        return user != null ? ok(Json.toJson(user)) : notFound();
    }
}
