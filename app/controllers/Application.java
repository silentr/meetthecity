package controllers;

import java.util.List;

import models.Tour;
import models.User;
import play.Logger;
import play.api.templates.Html;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.collection.mutable.StringBuilder;
import validators.Secured;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(new Html(new StringBuilder())));
    }

    public static Result tours() {

        List<Tour> tourList = Tour.find.all();
        return ok(tours.render(tourList));
    }

    public static Result getAllUsers() {
        List<User> user = User.find.all();

        return ok(Json.toJson(user));
    }

    public static Result getUser(String id) {
        User user = User.find.byId(id);
        return user != null ? ok(Json.toJson(user)) : notFound();
    }

    public static Result viewATour(Long id) {
        Tour tour = Tour.find.byId(id);
        String joined = "undefined";
        String username = session().get(UserManagment.SESSION_USERNAME);
        if (username != null) {
            User user = User.find.byId(username);
            joined = tour.tourists.contains(user) ? "true" : "false";
        }
        return ok(viewatour.render(tour, joined));
    }

    @Security.Authenticated(Secured.class)
    public static Result joinATour() {
        String tourId = Form.form().bindFromRequest().get("tourId");
        Tour tour = Tour.find.byId(Long.valueOf(tourId));
        String username = session().get(UserManagment.SESSION_USERNAME);
        User user = User.find.byId(username);
        return ok(tour.join(user).toString());
    }

    @Security.Authenticated(Secured.class)
    public static Result leaveATour() {
        String tourId = Form.form().bindFromRequest().get("tourId");
        Logger.info("tourId: " + tourId);
        Tour tour = Tour.find.byId(Long.valueOf(tourId));
        String username = session().get(UserManagment.SESSION_USERNAME);
        User user = User.find.byId(username);
        return ok(tour.leave(user).toString());
    }

    @Security.Authenticated(Secured.class)
    public static Result createATour() {
        return ok(createatour.render());
    }

    @Security.Authenticated(Secured.class)
    public static Result createATourSubmit() {
        return ok();
    }

}
