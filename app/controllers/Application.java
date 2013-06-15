package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Tour;
import models.User;
import play.Logger;
import play.api.templates.Html;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;
import views.html.index;
import views.html.joinatour;
import views.html.signin;
import views.html.user;
import views.html.tours;

public class Application extends Controller {

    public static class Login {

        public String username;
        
        public String password;

        public String validate() {
            Logger.debug("validating = " + username + ", password = " + password);
            
            return User.authenticate(username, password) == null ? "Invalid user or password" : null;
        }
    }

    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            
            Logger.debug("User didn't logged in");
            
            return badRequest(signin.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().username);
            
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
    
    public static Result tours() {

        List<Tour> tourList = Tour.find.all();
        return ok(tours.render(tourList));
    }
    
    public static Result createUser() {
        User user = Form.form(User.class).bindFromRequest().get();
        user.save();

        return redirect(routes.Application.index());
    }
    
    public static Result addUser() {
        return ok(user.render());
    }

    public static Result getAllUsers() {
        List<User> user = User.find.all();

        return ok(Json.toJson(user));
    }

    public static Result getUser(String id) {
        User user = User.find.byId(id);

        return user != null ? ok(Json.toJson(user)) : notFound();
    }
    
    public static Result joinATour(String id) {

        Logger.info(id);
		Tour tour = new Model.Finder<String, Tour>(String.class, Tour.class).byId("1");
		return ok(joinatour.render(tour));
	}
    

}
