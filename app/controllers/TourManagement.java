package controllers;

import static play.data.Form.form;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.Location;
import models.Tour;
import models.User;
import models.form.TourForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import validators.Secured;
import views.html.createatour;
import views.html.tours;
import views.html.viewatour;

public class TourManagement extends Controller {
    
    public static Result tours() {
        List<Tour> tourList = Tour.find.all();
        return ok(tours.render(tourList));
    }
    
    public static Result viewATour(Long id) {
        Tour tour = Tour.find.byId(id);
        if (tour == null) {
            return redirect(routes.TourManagement.tours());
        }
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
        if (username == null) {
            return redirect(routes.UserManagment.signin());
        }
        User user = User.find.byId(username);
        return ok(tour.join(user).toString());
    }

    @Security.Authenticated(Secured.class)
    public static Result leaveATour() {
        String tourId = form().bindFromRequest().get("tourId");
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
        Form<TourForm> tourForm = form(TourForm.class);
        return ok(createatour.render(tourForm, countries, cities));
    }

    @Security.Authenticated(Secured.class)
    public static Result createATourSubmit() {
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart image = body.getFile("imgFile");
        Form<TourForm> tourFormFilled = form(TourForm.class).bindFromRequest();
        if (tourFormFilled.hasErrors()) {
            List<String> countries = Location.findUniqueCountries();
            List<String> cities = new ArrayList<>();
            return badRequest(createatour.render(tourFormFilled, countries, cities));
        }
        if (image != null) {
            File file1 = image.getFile();
            TourForm tourForm = tourFormFilled.get();
            tourForm.photoName = file1.getAbsolutePath();
            String username = session().get("username");
            User guide = User.find.byId(username);
            Tour.create(tourForm, guide);
            return tours();
        } else {
            return redirect(routes.Application.home());
        }
    }

    public static Result changeCountry() {
        String country = form().bindFromRequest().get("country");
        List<String> cities = Location.findUniqueCitiesOfCountry(country);
        return ok(Json.toJson(cities));
    }
}
