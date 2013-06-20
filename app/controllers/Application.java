package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Location;
import models.Tour;
import models.User;
import models.form.TourForm;
import models.form.Login;
import models.form.SignUp;
import play.Logger;
import play.api.templates.Html;
import play.data.Form;
import static play.data.Form.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import scala.collection.mutable.StringBuilder;
import validators.Secured;
import views.html.createatour;
import views.html.index;
import views.html.signin;
import views.html.signup;
import views.html.tours;
import views.html.viewatour;

public class Application extends Controller {

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {

            Logger.debug("User didn't logged in, errors = " + loginForm.errors());

            return badRequest(signin.render(loginForm));
        } else {
            session().clear();
            session("username", loginForm.get().username);

            Logger.debug("User " + loginForm.get().username + " logged in");

            return redirect(routes.Application.index());
        }
    }

    public static Result index() {
        return ok(index.render(new Html(new StringBuilder())));
    }

    public static Result signin() {
        return ok(signin.render(form(Login.class)));
    }

    public static Result signupSubmit() {
        Form<SignUp> signUpForm = form(SignUp.class).bindFromRequest();

        if (signUpForm.hasErrors()) {
            Logger.debug("User didn't signed up errors = " + signUpForm.errors());

            return badRequest(signup.render(signUpForm));
        } else {
            User user = SignUp.createUser(signUpForm.get());
            user.save();
            Logger.debug(user + " signed up");

            return redirect(routes.Application.signin());
        }
    }

    public static Result signup() {
        return ok(signup.render(form(SignUp.class)));
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
        String tourId = form().bindFromRequest().get("tourId");
        Tour tour = Tour.find.byId(Long.valueOf(tourId));
        String username = session().get(UserManagment.SESSION_USERNAME);
        User user = User.find.byId(username);
        return ok(tour.join(user).toString());
    }

    @Security.Authenticated(Secured.class)
    public static Result leaveATour() {
        String tourId = form().bindFromRequest().get("tourId");
        Logger.info("tourId: " + tourId);
        Tour tour = Tour.find.byId(Long.valueOf(tourId));
        String username = session().get(UserManagment.SESSION_USERNAME);
        User user = User.find.byId(username);
        return ok(tour.leave(user).toString());
    }

    @Security.Authenticated(Secured.class)
    public static Result createATour() {
        List<String> countries = Location.findUniqueCountries();
        List<String> cities = new ArrayList<>();
        cities = new ArrayList<>();
        return ok(createatour.render(form(models.form.TourForm.class), countries, cities));
    }

    @Security.Authenticated(Secured.class)
    public static Result createATourSubmit() {
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart image = body.getFile("imgFile");
        Form<TourForm> form = form(TourForm.class).bindFromRequest();
        if(form.hasErrors()){
            return badRequest(form.errorsAsJson());
        }
        if (image != null) {
            File file1 = image.getFile();
            TourForm tourForm = form.get();
            tourForm.photoName = file1.getAbsolutePath();
            String username = session().get("username");
            User guide = User.find.byId(username);
            Tour.create(tourForm, guide);
            return tours();
        } else {
            flash("error", "Missing file");
            return redirect(routes.Application.index());
        }
    }

    public static Result changeCountry() {
        String country = form().bindFromRequest().get("country");
        List<String> cities = Location.findUniqueCitiesOfCountry(country);
        return ok(Json.toJson(cities));
    }

}
