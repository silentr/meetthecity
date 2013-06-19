package controllers;

import java.util.List;

import models.Review;
import models.Tour;
import models.User;
import models.form.Login;
import models.form.SignUp;
import play.Logger;
import play.api.templates.Html;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Security.Authenticated;
import scala.collection.mutable.StringBuilder;
import validators.Secured;
import views.html.*;
import com.avaje.ebean.Ebean;

public class Application extends Controller {

    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
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
        return ok(signin.render(Form.form(Login.class)));
    }

    public static Result signupSubmit() {
        Form<SignUp> signUpForm = Form.form(SignUp.class).bindFromRequest();

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
        return ok(signup.render(Form.form(SignUp.class)));
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

    public static Result viewATour(String id) {
        Tour tour = new Model.Finder<String, Tour>(String.class, Tour.class).byId(id);
        String joined = "undefined";
        String username = session().get("username");
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
        String username = session().get("username");
        User user = User.find.byId(username);
        return ok(tour.join(user).toString());
    }

    @Security.Authenticated(Secured.class)
    public static Result leaveATour() {
        String tourId = Form.form().bindFromRequest().get("tourId");
        Logger.info("tourId: " + tourId);
        Tour tour = Tour.find.byId(Long.valueOf(tourId));
        String username = session().get("username");
        User user = User.find.byId(username);
        return ok(tour.leave(user).toString());
    }
}
