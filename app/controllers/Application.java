package controllers;

import java.util.List;

import models.User;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.signin;

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
        return ok(index.render("Your new application is ready."));
    }

    public static Result signin() {
        return ok(signin.render(Form.form(Login.class)));
    }

    public static Result createUser() {
        User user = Form.form(User.class).bindFromRequest().get();
        user.save();

        return redirect(routes.Application.index());
    }

    public static Result getAllUsers() {
        List<User> user = new Model.Finder<String, User>(String.class, User.class).all();

        return ok(Json.toJson(user));
    }

    public static Result getUser(String id) {
        User user = new Model.Finder<String, User>(String.class, User.class).byId(id);

        return user != null ? ok(Json.toJson(user)) : notFound();
    }
}
